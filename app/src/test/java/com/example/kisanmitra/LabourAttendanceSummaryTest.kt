package com.example.kisanmitra

import com.example.kisanmitra.utils.LabourAttendanceSummaryCalculator
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class LabourAttendanceSummaryTest {

    @Test
    fun calculatesAttendanceSummary() {
        val result = LabourAttendanceSummaryCalculator.calculate(
            listOf(true, true, false, true, false)
        )

        assertEquals(5, result.totalDays)
        assertEquals(3, result.presentDays)
        assertEquals(2, result.absentDays)
        assertEquals(60.0, result.attendancePercentage, 0.001)
    }

    @Test
    fun calculatesHundredPercentAttendance() {
        val result = LabourAttendanceSummaryCalculator.calculate(
            listOf(true, true, true, true)
        )

        assertEquals(4, result.totalDays)
        assertEquals(4, result.presentDays)
        assertEquals(0, result.absentDays)
        assertEquals(100.0, result.attendancePercentage, 0.001)
    }

    @Test
    fun calculatesZeroPercentAttendance() {
        val result = LabourAttendanceSummaryCalculator.calculate(
            listOf(false, false, false)
        )

        assertEquals(3, result.totalDays)
        assertEquals(0, result.presentDays)
        assertEquals(3, result.absentDays)
        assertEquals(0.0, result.attendancePercentage, 0.001)
    }

    @Test
    fun calculatesSingleDayAttendance() {
        val result = LabourAttendanceSummaryCalculator.calculate(
            listOf(true)
        )

        assertEquals(1, result.totalDays)
        assertEquals(1, result.presentDays)
        assertEquals(0, result.absentDays)
        assertEquals(100.0, result.attendancePercentage, 0.001)
    }

    @Test
    fun rejectsEmptyAttendanceRecords() {
        assertThrows(IllegalArgumentException::class.java) {
            LabourAttendanceSummaryCalculator.calculate(emptyList())
        }
    }
}