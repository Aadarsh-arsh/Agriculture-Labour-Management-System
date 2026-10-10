
package com.example.kisanmitra

import com.example.kisanmitra.utils.LabourManagementAnalytics
import com.example.kisanmitra.utils.LabourRecord
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertThrows
import org.junit.Test

class LabourManagementAnalyticsTest {

    @Test
    fun summarizesMultipleWorkers() {
        val result = LabourManagementAnalytics.summarize(
            listOf(
                LabourRecord("Ramesh", 4, 500.0),
                LabourRecord("Suresh", 3, 600.0),
                LabourRecord("Ramesh", 2, 500.0)
            )
        )

        assertEquals(3, result.recordCount)
        assertEquals(9, result.totalWorkerDays)
        assertEquals(4100.0, result.totalCost, 0.001)
        assertEquals(4100.0 / 9.0, result.averageDailyRate, 0.001)

        val highestCostWorker = result.highestCostWorker
        assertNotNull(highestCostWorker)
        assertEquals("Ramesh", highestCostWorker?.workerName)
        assertEquals(6, highestCostWorker?.totalDays)
        assertEquals(3000.0, highestCostWorker?.totalCost ?: 0.0, 0.001)
    }

    @Test
    fun handlesEmptyRecords() {
        val result = LabourManagementAnalytics.summarize(emptyList())

        assertEquals(0, result.recordCount)
        assertEquals(0, result.totalWorkerDays)
        assertEquals(0.0, result.totalCost, 0.001)
        assertEquals(0.0, result.averageDailyRate, 0.001)
        assertNull(result.highestCostWorker)
    }

    @Test
    fun combinesRecordsForNamesWithSurroundingWhitespace() {
        val result = LabourManagementAnalytics.summarize(
            listOf(
                LabourRecord(" Ramesh ", 2, 500.0),
                LabourRecord("Ramesh", 1, 500.0)
            )
        )

        val worker = result.highestCostWorker
        assertNotNull(worker)
        assertEquals("Ramesh", worker?.workerName)
        assertEquals(3, worker?.totalDays)
        assertEquals(1500.0, worker?.totalCost ?: 0.0, 0.001)
    }

    @Test
    fun rejectsNegativeDaysWorked() {
        assertThrows(IllegalArgumentException::class.java) {
            LabourManagementAnalytics.summarize(
                listOf(LabourRecord("Ramesh", -1, 500.0))
            )
        }
    }

    @Test
    fun rejectsNaNDailyRate() {
        assertThrows(IllegalArgumentException::class.java) {
            LabourManagementAnalytics.summarize(
                listOf(LabourRecord("Ramesh", 2, Double.NaN))
            )
        }
    }

    @Test
    fun rejectsBlankWorkerName() {
        assertThrows(IllegalArgumentException::class.java) {
            LabourManagementAnalytics.summarize(
                listOf(LabourRecord("   ", 2, 500.0))
            )
        }
    }

    @Test
    fun rejectsInfiniteDailyRate() {
        assertThrows(IllegalArgumentException::class.java) {
            LabourManagementAnalytics.summarize(
                listOf(
                    LabourRecord("Ramesh", 2, Double.POSITIVE_INFINITY)
                )
            )
        }
    }
}
