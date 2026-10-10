package com.example.kisanmitra

import com.example.kisanmitra.utils.LabourWageCalculator
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class LabourWageCalculatorTest {

    @Test
    fun calculatesWageForWorkedDays() {
        assertEquals(
            3000.0,
            LabourWageCalculator.calculateWage(6, 500.0),
            0.001
        )
    }

    @Test
    fun calculatesZeroWageForZeroWorkedDays() {
        assertEquals(
            0.0,
            LabourWageCalculator.calculateWage(0, 500.0),
            0.001
        )
    }

    @Test
    fun rejectsNegativeDaysWorked() {
        assertThrows(IllegalArgumentException::class.java) {
            LabourWageCalculator.calculateWage(-1, 500.0)
        }
    }

    @Test
    fun rejectsNegativeDailyRate() {
        assertThrows(IllegalArgumentException::class.java) {
            LabourWageCalculator.calculateWage(5, -100.0)
        }
    }

    @Test
    fun rejectsNonFiniteDailyRate() {
        assertThrows(IllegalArgumentException::class.java) {
            LabourWageCalculator.calculateWage(5, Double.NaN)
        }
    }
}
