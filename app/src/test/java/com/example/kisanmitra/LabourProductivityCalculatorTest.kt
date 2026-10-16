
package com.example.kisanmitra

import com.example.kisanmitra.utils.LabourProductivityCalculator
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Test

class LabourProductivityCalculatorTest {

    @Test
    fun calculatesOutputPerWorkerHour() {
        val result = LabourProductivityCalculator.calculate(
            completedOutput = 120.0,
            workerCount = 4,
            hoursPerWorker = 6.0
        )

        assertEquals(24.0, result.totalWorkerHours, 0.001)
        assertEquals(5.0, result.outputPerWorkerHour, 0.001)
        assertEquals(0.2, result.workerHoursPerOutputUnit, 0.001)
    }

    @Test
    fun rejectsZeroWorkers() {
        try {
            LabourProductivityCalculator.calculate(10.0, 0, 8.0)
            fail("Expected invalid worker count to be rejected")
        } catch (_: IllegalArgumentException) {
            // Expected
        }
    }

    @Test
    fun handlesZeroOutput() {
        val result = LabourProductivityCalculator.calculate(
            completedOutput = 0.0,
            workerCount = 2,
            hoursPerWorker = 8.0
        )

        assertEquals(0.0, result.outputPerWorkerHour, 0.001)
        assertEquals(0.0, result.workerHoursPerOutputUnit, 0.001)
    }
}
