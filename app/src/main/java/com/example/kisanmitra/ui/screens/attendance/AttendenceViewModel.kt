package com.example.kisanmitra.ui.screens.attendance

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.kisanmitra.data.Attendance
import com.example.kisanmitra.data.AttendanceRepository
import com.example.kisanmitra.data.KisanMitraDatabase
import com.example.kisanmitra.data.Labour
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class AttendanceViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val database =
        KisanMitraDatabase.getDatabase(application)

    private val repository =
        AttendanceRepository(database.attendanceDao())

    val attendance = repository.allAttendance

    val labourers: Flow<List<Labour>> =
        database.labourDao().getAllLabourers()

    fun markAttendance(
        labourId: Int,
        labourName: String,
        date: String,
        status: String,
        taskName: String
    ) {

        val cleanLabourName = labourName.trim()
        val cleanDate = date.trim()
        val cleanStatus = status.trim()
        val cleanTaskName = taskName.trim()

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

            val existingCount =
                repository.getAttendanceCount(
                    labourId = labourId,
                    date = cleanDate,
                    taskName = cleanTaskName
                )

            // Prevent duplicate attendance
            if (existingCount > 0) {
                return@launch
            }

            repository.insertAttendance(
                Attendance(
                    labourId = labourId,
                    labourName = cleanLabourName,
                    date = cleanDate,
                    status = cleanStatus,
                    taskName = cleanTaskName
                )
            )
        }
    }

    fun deleteAttendance(attendance: Attendance) {

        viewModelScope.launch {
            repository.deleteAttendance(attendance)
        }
    }
}