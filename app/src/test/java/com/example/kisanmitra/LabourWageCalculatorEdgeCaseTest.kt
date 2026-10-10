package com.example.kisanmitra

import com.example.kisanmitra.utils.LabourWageCalculator
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class LabourWageCalculatorEdgeCaseTest {

    @Test
    fun calculatesWageForOneDay() {
        assertEquals(
            500.0,
            LabourWageCalculator.calculateWage(1, 500.0),
            0.001
        )
    }

    @Test
    fun acceptsZeroDailyRate() {
        assertEquals(
            0.0,
            LabourWageCalculator.calculateWage(5, 0.0),
            0.001
        )
    }

    @Test
    fun rejectsInfiniteDailyRate() {
        assertThrows(IllegalArgumentException::class.java) {
            LabourWageCalculator.calculateWage(5, Double.POSITIVE_INFINITY)
        }
    }

    @Test
    fun rejectsNaNDailyRate() {
        assertThrows(IllegalArgumentException::class.java) {
            LabourWageCalculator.calculateWage(5, Double.NaN)
        }
    }
}