package com.example.kisanmitra.data

import kotlinx.coroutines.flow.Flow

class AttendanceRepository(
    private val attendanceDao: AttendanceDao
) {

    val allAttendance: Flow<List<Attendance>> =
        attendanceDao.getAllAttendance()

    suspend fun insertAttendance(attendance: Attendance) {
        attendanceDao.insertAttendance(attendance)
    }

    suspend fun deleteAttendance(attendance: Attendance) {
        attendanceDao.deleteAttendance(attendance)
    }
}