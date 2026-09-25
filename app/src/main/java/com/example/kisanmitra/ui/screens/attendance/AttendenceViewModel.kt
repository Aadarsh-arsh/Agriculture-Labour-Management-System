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

    // =========================================================
    // ATTENDANCE
    // =========================================================

    private val _attendance =
        MutableStateFlow<List<Attendance>>(emptyList())

    val attendance: StateFlow<List<Attendance>> =
        _attendance.asStateFlow()

    // =========================================================
    // LABOURERS
    // =========================================================

    private val _labourers =
        MutableStateFlow<List<Labour>>(emptyList())

    val labourers: StateFlow<List<Labour>> =
        _labourers.asStateFlow()

    // =========================================================
    // ERROR
    // =========================================================

    private val _errorMessage =
        MutableStateFlow<String?>(null)

    val errorMessage: StateFlow<String?> =
        _errorMessage.asStateFlow()

    // =========================================================
    // INIT
    // =========================================================

    init {
        loadAttendance()
        loadLabourers()
    }

    // =========================================================
    // LOAD LABOURERS
    // =========================================================

    fun loadLabourers() {

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

                        _labourers.value =
                            localLabourers
                    }
            }
        }
    }

    // =========================================================
    // LOAD ATTENDANCE
    // =========================================================

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

                localRepository
                    .allAttendance
                    .collect { localAttendance ->

                        _attendance.value =
                            localAttendance
                    }
            }
        }
    }

    // =========================================================
    // MARK ATTENDANCE
    // =========================================================
    //
    // IMPORTANT:
    // This is now a suspend function and RETURNS Boolean.
    //
    // true  = attendance successfully saved
    // false = attendance was not saved
    //
    // AttendanceScreen will move to the next labour ONLY
    // when this function returns true.
    // =========================================================

    suspend fun markAttendance(
        labourId: Int,
        labourName: String,
        date: String,
        status: String,
        taskName: String
    ): Boolean {

        _errorMessage.value = null

        val cleanLabourName =
            labourName.trim()

        val cleanDate =
            date.trim()

        val cleanStatus =
            status.trim()

        val cleanTaskName =
            taskName.trim()

        // =====================================================
        // VALIDATION
        // =====================================================

        if (
            labourId <= 0 ||
            cleanLabourName.isBlank()
        ) {

            _errorMessage.value =
                "Please select a labourer"

            return false
        }

        if (cleanDate.isBlank()) {

            _errorMessage.value =
                "Please enter attendance date"

            return false
        }

        if (cleanStatus.isBlank()) {

            _errorMessage.value =
                "Please select attendance status"

            return false
        }

        if (cleanTaskName.isBlank()) {

            _errorMessage.value =
                "Please select agricultural task"

            return false
        }

        // =====================================================
        // SAVE
        // =====================================================

        return try {

            // -------------------------------------------------
            // GET EXISTING ATTENDANCE
            // -------------------------------------------------

            val existingAttendance =
                supabaseRepository.getAttendance()

            // -------------------------------------------------
            // FIND LABOUR
            // -------------------------------------------------

            val labour =
                labourRepository
                    .getLabourers()
                    .firstOrNull {
                        it.id == labourId.toLong()
                    }

            if (labour == null) {

                _errorMessage.value =
                    "Labourer not found"

                return false
            }

            // -------------------------------------------------
            // FIND TASK
            // -------------------------------------------------

            val task =
                taskRepository
                    .getTasks()
                    .firstOrNull {

                        it.task_name.equals(
                            cleanTaskName,
                            ignoreCase = true
                        )
                    }

            if (task == null) {

                _errorMessage.value =
                    "Agricultural task not found"

                return false
            }

            // -------------------------------------------------
            // DUPLICATE CHECK
            // -------------------------------------------------

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

                _errorMessage.value =
                    "Attendance already marked for this labourer"

                return false
            }

            // -------------------------------------------------
            // INSERT INTO SUPABASE
            // -------------------------------------------------

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

            // -------------------------------------------------
            // REFRESH ATTENDANCE HISTORY
            // -------------------------------------------------

            loadAttendance()

            true

        } catch (e: Exception) {

            Log.e(
                "ATTENDANCE_SUPABASE",
                "INSERT FAILED: ${e.message}",
                e
            )

            // =================================================
            // ROOM FALLBACK
            // =================================================

            try {

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

                    _errorMessage.value =
                        "Attendance already marked for this labourer"

                    return false
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

                Log.d(
                    "ATTENDANCE_LOCAL",
                    "LOCAL INSERT SUCCESS"
                )

                true

            } catch (localException: Exception) {

                Log.e(
                    "ATTENDANCE_LOCAL",
                    "LOCAL INSERT FAILED: ${localException.message}",
                    localException
                )

                _errorMessage.value =
                    "Unable to save attendance"

                false
            }
        }
    }

    // =========================================================
    // DELETE ATTENDANCE
    // =========================================================

    fun deleteAttendance(
        attendance: Attendance
    ) {

        _errorMessage.value = null

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

                try {

                    localRepository.deleteAttendance(
                        attendance
                    )

                } catch (localException: Exception) {

                    Log.e(
                        "ATTENDANCE_SUPABASE",
                        "LOCAL DELETE FAILED: ${localException.message}",
                        localException
                    )

                    _errorMessage.value =
                        "Unable to delete attendance"
                }
            }
        }
    }

    // =========================================================
    // CLEAR ERROR
    // =========================================================

    fun clearError() {
        _errorMessage.value = null
    }

    // =========================================================
    // DATE CONVERSION
    // =========================================================

    private fun convertDateForDisplay(
        date: String
    ): String {

        val parts =
            date.split("-")

        if (parts.size != 3) {
            return date
        }

        val year =
            parts[0]

        val month =
            parts[1]

        val day =
            parts[2]

        return "$day/$month/$year"
    }
}