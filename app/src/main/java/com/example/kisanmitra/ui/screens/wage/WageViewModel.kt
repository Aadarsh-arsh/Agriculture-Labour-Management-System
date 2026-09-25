package com.example.kisanmitra.ui.screens.wage

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.kisanmitra.data.Attendance
import com.example.kisanmitra.data.KisanMitraDatabase
import com.example.kisanmitra.data.Labour
import com.example.kisanmitra.data.SupabaseAttendanceRepository
import com.example.kisanmitra.data.SupabaseLabourRepository
import com.example.kisanmitra.data.SupabaseWage
import com.example.kisanmitra.data.SupabaseWageRepository
import com.example.kisanmitra.data.Wage
import com.example.kisanmitra.data.WageRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WageViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val database =
        KisanMitraDatabase.getDatabase(application)

    private val localRepository =
        WageRepository(database.wageDao())

    private val wageRepository =
        SupabaseWageRepository()

    private val labourRepository =
        SupabaseLabourRepository()

    private val attendanceRepository =
        SupabaseAttendanceRepository()

    private val _wages =
        MutableStateFlow<List<Wage>>(emptyList())

    val wages: StateFlow<List<Wage>> =
        _wages.asStateFlow()

    private val _labourers =
        MutableStateFlow<List<Labour>>(emptyList())

    val labourers: StateFlow<List<Labour>> =
        _labourers.asStateFlow()

    private val _attendance =
        MutableStateFlow<List<Attendance>>(emptyList())

    val attendance: StateFlow<List<Attendance>> =
        _attendance.asStateFlow()

    init {
        loadWages()
        loadLabourers()
        loadAttendance()
    }

    private fun loadWages() {

        viewModelScope.launch {

            try {

                val supabaseWages =
                    wageRepository.getWages()

                _wages.value =
                    supabaseWages.map { wage ->

                        Wage(
                            id = wage.id?.toInt() ?: 0,
                            labourId = wage.labour_id.toInt(),
                            labourName = wage.labour_name,
                            dailyWage = wage.daily_wage,
                            presentDays = wage.present_days,
                            totalWage = wage.total_wage
                        )
                    }

            } catch (e: Exception) {

                Log.e(
                    "WAGE_SUPABASE",
                    "LOAD FAILED: ${e.message}",
                    e
                )

                localRepository.allWages.collect {
                    _wages.value = it
                }
            }
        }
    }

    private fun loadLabourers() {

        viewModelScope.launch {

            try {

                val supabaseLabourers =
                    labourRepository.getLabourers()

                _labourers.value =
                    supabaseLabourers.map { labour ->

                        Labour(
                            id = labour.id?.toInt() ?: 0,
                            name = labour.name,
                            phone = labour.phone,
                            dailyWage = labour.daily_wage,
                            skill = labour.skill
                        )
                    }

            } catch (e: Exception) {

                Log.e(
                    "WAGE_SUPABASE",
                    "LABOUR LOAD FAILED: ${e.message}",
                    e
                )

                database
                    .labourDao()
                    .getAllLabourers()
                    .collect {
                        _labourers.value = it
                    }
            }
        }
    }

    private fun loadAttendance() {

        viewModelScope.launch {

            try {

                val supabaseAttendance =
                    attendanceRepository.getAttendance()

                val labourers =
                    labourRepository.getLabourers()

                val tasks =
                    com.example.kisanmitra.data
                        .SupabaseAgriculturalTaskRepository()
                        .getTasks()

                _attendance.value =
                    supabaseAttendance.map { record ->

                        val labourName =
                            labourers
                                .firstOrNull {
                                    it.id == record.labour_id
                                }
                                ?.name
                                ?: "Unknown Labour"

                        val taskName =
                            tasks
                                .firstOrNull {
                                    it.id == record.task_id
                                }
                                ?.task_name
                                ?: "Unknown Task"

                        Attendance(
                            id = record.id?.toInt() ?: 0,
                            labourId = record.labour_id.toInt(),
                            labourName = labourName,
                            date = convertDateForDisplay(record.date),
                            status = record.status,
                            taskName = taskName
                        )
                    }

            } catch (e: Exception) {

                Log.e(
                    "WAGE_SUPABASE",
                    "ATTENDANCE LOAD FAILED: ${e.message}",
                    e
                )

                database
                    .attendanceDao()
                    .getAllAttendance()
                    .collect {
                        _attendance.value = it
                    }
            }
        }
    }

    fun calculateAndSaveWage(
        labour: Labour,
        attendanceList: List<Attendance>
    ) {

        if (
            labour.id <= 0 ||
            labour.dailyWage <= 0
        ) {
            return
        }

        val presentDays =
            attendanceList.count {

                it.labourId == labour.id &&
                        it.status
                            .trim()
                            .equals(
                                "Present",
                                ignoreCase = true
                            )
            }

        if (presentDays <= 0) {
            return
        }

        val totalWage =
            presentDays * labour.dailyWage

        viewModelScope.launch {

            try {

                val existingWages =
                    wageRepository.getWages()

                existingWages
                    .filter {
                        it.labour_id ==
                                labour.id.toLong()
                    }
                    .forEach { wage ->

                        wage.id?.let {
                            wageRepository.deleteWage(it)
                        }
                    }

                wageRepository.addWage(
                    labourId = labour.id.toLong(),
                    labourName = labour.name.trim(),
                    dailyWage = labour.dailyWage,
                    presentDays = presentDays
                )

                Log.d(
                    "WAGE_SUPABASE",
                    "INSERT SUCCESS"
                )

                loadWages()

            } catch (e: Exception) {

                Log.e(
                    "WAGE_SUPABASE",
                    "INSERT FAILED: ${e.message}",
                    e
                )

                localRepository.deleteWagesForLabour(
                    labour.id
                )

                localRepository.insertWage(
                    Wage(
                        labourId = labour.id,
                        labourName = labour.name.trim(),
                        dailyWage = labour.dailyWage,
                        presentDays = presentDays,
                        totalWage = totalWage
                    )
                )
            }
        }
    }

    fun deleteWage(wage: Wage) {

        viewModelScope.launch {

            try {

                wageRepository.deleteWage(
                    wage.id.toLong()
                )

                Log.d(
                    "WAGE_SUPABASE",
                    "DELETE SUCCESS"
                )

                loadWages()

            } catch (e: Exception) {

                Log.e(
                    "WAGE_SUPABASE",
                    "DELETE FAILED: ${e.message}",
                    e
                )

                localRepository.deleteWage(wage)
            }
        }
    }

    private fun convertDateForDisplay(
        date: String
    ): String {

        val parts =
            date.split("-")

        if (parts.size != 3) {
            return date
        }

        val year = parts[0]
        val month = parts[1]
        val day = parts[2]

        return "$day/$month/$year"
    }
}