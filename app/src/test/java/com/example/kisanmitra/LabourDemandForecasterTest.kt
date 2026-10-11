
package com.example.kisanmitra

import com.example.kisanmitra.utils.LabourDemandForecaster
import org.junit.Assert.assertEquals
import org.junit.Test

class LabourDemandForecasterTest {

    @Test
    fun forecastsFromRecentCounts() {
        val result = LabourDemandForecaster.forecast(
            listOf(4, 6, 8, 10),
            periodsToAverage = 3
        )

        assertEquals(8, result)
    }

    @Test
    fun roundsFractionalDemandUp() {
        val result = LabourDemandForecaster.forecast(
            listOf(3, 4),
            periodsToAverage = 2
        )

        assertEquals(4, result)
    }

    @Test(expected = IllegalArgumentException::class)
    fun rejectsNegativeCounts() {
        LabourDemandForecaster.forecast(listOf(3, -1, 5))
    }

    @Test(expected = IllegalArgumentException::class)
    fun rejectsEmptyHistory() {
        LabourDemandForecaster.forecast(emptyList())
    }
}
