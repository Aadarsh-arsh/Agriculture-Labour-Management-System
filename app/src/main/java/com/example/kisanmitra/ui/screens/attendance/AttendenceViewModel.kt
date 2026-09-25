package com.example.kisanmitra.ui.screens.attendance

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.kisanmitra.data.Attendance
import com.example.kisanmitra.data.AttendanceRepository
import com.example.kisanmitra.data.KisanMitraDatabase
import com.example.kisanmitra.data.Labour
import com.example.kisanmitra.data.SupabaseAttendanceRepository
import com.example.kisanmitra.data.SupabaseAgriculturalTaskRepository
import com.example.kisanmitra.data.SupabaseLabourRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AttendanceViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val database =
        KisanMitraDatabase.getDatabase(application)

    private val localRepository =
        AttendanceRepository(
            database.attendanceDao()
        )

    private val supabaseRepository =
        SupabaseAttendanceRepository()

    private val labourRepository =
        SupabaseLabourRepository()

    private val taskRepository =
        SupabaseAgriculturalTaskRepository()

    private val _attendance =
        MutableStateFlow<List<Attendance>>(emptyList())

    val attendance: StateFlow<List<Attendance>> =
        _attendance.asStateFlow()

    private val _labourers =
        MutableStateFlow<List<Labour>>(emptyList())

    val labourers: StateFlow<List<Labour>> =
        _labourers.asStateFlow()

    init {
        loadAttendance()
        loadLabourers()
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
                    "ATTENDANCE_SUPABASE",
                    "LABOUR LOAD FAILED: ${e.message}",
                    e
                )

                database
                    .labourDao()
                    .getAllLabourers()
                    .collect { localLabourers ->
                        _labourers.value = localLabourers
                    }
            }
        }
    }

    private fun loadAttendance() {

        viewModelScope.launch {

            try {

                val supabaseAttendance =
                    supabaseRepository.getAttendance()

                val labourers =
                    labourRepository.getLabourers()

                val tasks =
                    taskRepository.getTasks()

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
                            id =
                                record.id?.toInt()
                                    ?: 0,
                            labourId =
                                record.labour_id.toInt(),
                            labourName =
                                labourName,
                            date =
                                convertDateForDisplay(
                                    record.date
                                ),
                            status =
                                record.status,
                            taskName =
                                taskName
                        )
                    }

            } catch (e: Exception) {

                Log.e(
                    "ATTENDANCE_SUPABASE",
                    "LOAD FAILED: ${e.message}",
                    e
                )

                localRepository.allAttendance.collect { localAttendance ->
                    _attendance.value = localAttendance
                }
            }
        }
    }

    fun markAttendance(
        labourId: Int,
        labourName: String,
        date: String,
        status: String,
        taskName: String
    ) {

        val cleanLabourName =
            labourName.trim()

        val cleanDate =
            date.trim()

        val cleanStatus =
            status.trim()

        val cleanTaskName =
            taskName.trim()

        if (
            labourId <= 0 ||
            cleanLabourName.isBlank() ||
            cleanDate.isBlank() ||
            cleanStatus.isBlank() ||
            cleanTaskName.isBlank()
        ) {
            return
        }

        viewModelScope.launch {

            try {

                /*
                 * Check Supabase first to prevent
                 * duplicate attendance.
                 */
                val existingAttendance =
                    supabaseRepository
                        .getAttendance()

                val labour =
                    labourRepository
                        .getLabourers()
                        .firstOrNull {
                            it.id == labourId.toLong()
                        }

                val task =
                    taskRepository
                        .getTasks()
                        .firstOrNull {
                            it.task_name.equals(
                                cleanTaskName,
                                ignoreCase = true
                            )
                        }

                if (
                    labour != null &&
                    task != null
                ) {

                    val duplicate =
                        existingAttendance.any { record ->

                            record.labour_id ==
                                    labour.id &&
                                    record.task_id ==
                                    task.id &&
                                    convertDateForDisplay(
                                        record.date
                                    ) == cleanDate
                        }

                    if (duplicate) {
                        return@launch
                    }
                }

                supabaseRepository.addAttendance(
                    labourName =
                        cleanLabourName,
                    taskName =
                        cleanTaskName,
                    date =
                        cleanDate,
                    status =
                        cleanStatus
                )

                Log.d(
                    "ATTENDANCE_SUPABASE",
                    "INSERT SUCCESS"
                )

                loadAttendance()

            } catch (e: Exception) {

                Log.e(
                    "ATTENDANCE_SUPABASE",
                    "INSERT FAILED: ${e.message}",
                    e
                )

                // Local fallback with existing
                // duplicate protection.
                val existingCount =
                    localRepository
                        .getAttendanceCount(
                            labourId =
                                labourId,
                            date =
                                cleanDate,
                            taskName =
                                cleanTaskName
                        )

                if (existingCount > 0) {
                    return@launch
                }

                localRepository.insertAttendance(
                    Attendance(
                        labourId =
                            labourId,
                        labourName =
                            cleanLabourName,
                        date =
                            cleanDate,
                        status =
                            cleanStatus,
                        taskName =
                            cleanTaskName
                    )
                )
            }
        }
    }

    fun deleteAttendance(
        attendance: Attendance
    ) {

        viewModelScope.launch {

            try {

                supabaseRepository.deleteAttendance(
                    attendance.id.toLong()
                )

                Log.d(
                    "ATTENDANCE_SUPABASE",
                    "DELETE SUCCESS"
                )

                loadAttendance()

            } catch (e: Exception) {

                Log.e(
                    "ATTENDANCE_SUPABASE",
                    "DELETE FAILED: ${e.message}",
                    e
                )

                localRepository.deleteAttendance(
                    attendance
                )
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