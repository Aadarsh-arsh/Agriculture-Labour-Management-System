
package com.example.kisanmitra

import com.example.kisanmitra.utils.CropTaskWorkEstimator
import org.junit.Assert.assertEquals
import org.junit.Test

class CropTaskWorkEstimatorTest {

    @Test
    fun estimatesTaskDuration() {
        val result = CropTaskWorkEstimator.estimate(
            area = 12.0,
            productivityPerWorkerHour = 2.0,
            workerCount = 3,
            hoursPerDay = 8.0
        )

        assertEquals(6.0, result.totalWorkerHours, 0.001)
        assertEquals(2.0, result.estimatedHours, 0.001)
        assertEquals(1, result.estimatedDays)
    }

    @Test
    fun roundsPartialWorkDayUp() {
        val result = CropTaskWorkEstimator.estimate(
            area = 20.0,
            productivityPerWorkerHour = 1.0,
            workerCount = 2,
            hoursPerDay = 8.0
        )

        assertEquals(10.0, result.estimatedHours, 0.001)
        assertEquals(2, result.estimatedDays)
    }

    @Test(expected = IllegalArgumentException::class)
    fun rejectsZeroProductivity() {
        CropTaskWorkEstimator.estimate(
            area = 10.0,
            productivityPerWorkerHour = 0.0,
            workerCount = 2
        )
    }

    @Test
    fun zeroAreaRequiresNoDays() {
        val result = CropTaskWorkEstimator.estimate(
            area = 0.0,
            productivityPerWorkerHour = 1.0,
            workerCount = 2
        )

        assertEquals(0, result.estimatedDays)
    }
}
