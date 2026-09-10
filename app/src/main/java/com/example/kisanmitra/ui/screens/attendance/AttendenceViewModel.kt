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

    // Get all registered labourers
    val labourers: Flow<List<Labour>> =
        database.labourDao().getAllLabourers()

    fun markAttendance(
        labourId: Int,
        labourName: String,
        date: String,
        status: String,
        taskName: String
    ) {
        if (
            labourName.isBlank() ||
            date.isBlank() ||
            status.isBlank() ||
            taskName.isBlank()
        ) {
            return
        }

        viewModelScope.launch {
            repository.insertAttendance(
                Attendance(
                    labourId = labourId,
                    labourName = labourName,
                    date = date,
                    status = status,
                    taskName = taskName
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