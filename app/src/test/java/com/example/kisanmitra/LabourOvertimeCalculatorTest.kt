package com.example.kisanmitra

import com.example.kisanmitra.utils.LabourOvertimeCalculator
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class LabourOvertimeCalculatorTest {

    @Test
    fun calculatesOvertimePay() {
        val result = LabourOvertimeCalculator.calculate(
            regularHours = 8.0,
            actualHours = 10.0,
            hourlyRate = 100.0
        )

        assertEquals(2.0, result.overtimeHours, 0.001)
        assertEquals(150.0, result.overtimeRate, 0.001)
        assertEquals(300.0, result.overtimePay, 0.001)
    }

    @Test
    fun returnsZeroOvertimeWhenActualHoursEqualRegularHours() {
        val result = LabourOvertimeCalculator.calculate(
            regularHours = 8.0,
            actualHours = 8.0,
            hourlyRate = 100.0
        )

        assertEquals(0.0, result.overtimeHours, 0.001)
        assertEquals(0.0, result.overtimePay, 0.001)
    }

    @Test
    fun returnsZeroOvertimeWhenActualHoursAreBelowRegularHours() {
        val result = LabourOvertimeCalculator.calculate(
            regularHours = 8.0,
            actualHours = 6.0,
            hourlyRate = 100.0
        )

        assertEquals(0.0, result.overtimeHours, 0.001)
        assertEquals(0.0, result.overtimePay, 0.001)
    }

    @Test
    fun supportsCustomOvertimeMultiplier() {
        val result = LabourOvertimeCalculator.calculate(
            regularHours = 8.0,
            actualHours = 9.0,
            hourlyRate = 200.0,
            overtimeMultiplier = 2.0
        )

        assertEquals(1.0, result.overtimeHours, 0.001)
        assertEquals(400.0, result.overtimeRate, 0.001)
        assertEquals(400.0, result.overtimePay, 0.001)
    }

    @Test
    fun rejectsNegativeRegularHours() {
        assertThrows(IllegalArgumentException::class.java) {
            LabourOvertimeCalculator.calculate(
                regularHours = -1.0,
                actualHours = 8.0,
                hourlyRate = 100.0
            )
        }
    }

    @Test
    fun rejectsNegativeActualHours() {
        assertThrows(IllegalArgumentException::class.java) {
            LabourOvertimeCalculator.calculate(
                regularHours = 8.0,
                actualHours = -1.0,
                hourlyRate = 100.0
            )
        }
    }

    @Test
    fun rejectsNegativeHourlyRate() {
        assertThrows(IllegalArgumentException::class.java) {
            LabourOvertimeCalculator.calculate(
                regularHours = 8.0,
                actualHours = 9.0,
                hourlyRate = -100.0
            )
        }
    }

    @Test
    fun rejectsInvalidOvertimeMultiplier() {
        assertThrows(IllegalArgumentException::class.java) {
            LabourOvertimeCalculator.calculate(
                regularHours = 8.0,
                actualHours = 9.0,
                hourlyRate = 100.0,
                overtimeMultiplier = 0.0
            )
        }
    }
}