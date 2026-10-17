package com.example.kisanmitra.utils.farm

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class HarvestYieldEstimatorTest {

    @Test
    fun estimatesYieldFromSamplePlot() {
        val result =
            HarvestYieldEstimator.calculate(2.0, 4.0, 2.0)

        assertEquals(5.0, result.yieldTonnesPerHectare, 0.001)
        assertEquals(10_000.0, result.estimatedTotalHarvestKg, 0.001)
    }

    @Test
    fun returnsZeroForZeroHarvest() {
        val result =
            HarvestYieldEstimator.calculate(0.0, 10.0, 1.0)

        assertEquals(0.0, result.yieldTonnesPerHectare, 0.001)
        assertEquals(0.0, result.estimatedTotalHarvestKg, 0.001)
    }

    @Test
    fun rejectsZeroSampleArea() {
        assertThrows(IllegalArgumentException::class.java) {
            HarvestYieldEstimator.calculate(2.0, 0.0, 1.0)
        }
    }

    @Test
    fun rejectsNegativeFieldArea() {
        assertThrows(IllegalArgumentException::class.java) {
            HarvestYieldEstimator.calculate(2.0, 10.0, -1.0)
        }
    }
}