package com.example.kisanmitra

import com.example.kisanmitra.utils.LabourEntryCalculator
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class LabourEntryCalculatorTest {

    @Test
    fun calculatesCompleteLabourEntry() {
        val result = LabourEntryCalculator.calculate(
            workerName = "Ramesh",
            daysWorked = 6,
            dailyRate = 500.0
        )

        assertEquals("Ramesh", result.workerName)
        assertEquals(6, result.daysWorked)
        assertEquals(500.0, result.dailyRate, 0.001)
        assertEquals(3000.0, result.totalWage, 0.001)
    }

    @Test
    fun trimsWorkerNameBeforeReturningResult() {
        val result = LabourEntryCalculator.calculate(
            workerName = "  Ramesh  ",
            daysWorked = 2,
            dailyRate = 400.0
        )

        assertEquals("Ramesh", result.workerName)
        assertEquals(800.0, result.totalWage, 0.001)
    }

    @Test
    fun acceptsZeroDaysWorked() {
        val result = LabourEntryCalculator.calculate(
            workerName = "Ramesh",
            daysWorked = 0,
            dailyRate = 500.0
        )

        assertEquals(0.0, result.totalWage, 0.001)
    }

    @Test
    fun rejectsBlankWorkerName() {
        assertThrows(IllegalArgumentException::class.java) {
            LabourEntryCalculator.calculate(
                workerName = "   ",
                daysWorked = 5,
                dailyRate = 500.0
            )
        }
    }

    @Test
    fun rejectsNegativeDaysWorked() {
        assertThrows(IllegalArgumentException::class.java) {
            LabourEntryCalculator.calculate(
                workerName = "Ramesh",
                daysWorked = -1,
                dailyRate = 500.0
            )
        }
    }

    @Test
    fun rejectsInvalidDailyRate() {
        assertThrows(IllegalArgumentException::class.java) {
            LabourEntryCalculator.calculate(
                workerName = "Ramesh",
                daysWorked = 5,
                dailyRate = Double.NaN
            )
        }
    }
}