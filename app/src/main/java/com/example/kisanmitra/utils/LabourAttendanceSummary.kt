package com.example.kisanmitra.utils

data class LabourAttendanceSummary(
    val totalDays: Int,
    val presentDays: Int,
    val absentDays: Int,
    val attendancePercentage: Double
)

object LabourAttendanceSummaryCalculator {

    fun calculate(attendance: List<Boolean>): LabourAttendanceSummary {
        require(attendance.isNotEmpty()) {
            "Attendance records cannot be empty"
        }

        val totalDays = attendance.size
        val presentDays = attendance.count { it }
        val absentDays = totalDays - presentDays

        val attendancePercentage =
            (presentDays.toDouble() / totalDays) * 100.0

        return LabourAttendanceSummary(
            totalDays = totalDays,
            presentDays = presentDays,
            absentDays = absentDays,
            attendancePercentage = attendancePercentage
        )
    }
}