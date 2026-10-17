package com.example.kisanmitra.utils.farm

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class PostHarvestLossCalculatorTest {

    @Test
    fun calculatesLostAndRemainingProduce() {
        val result =
            PostHarvestLossCalculator.calculate(1_000.0, 5.0)

        assertEquals(50.0, result.lostQuantityKg, 0.001)
        assertEquals(950.0, result.remainingQuantityKg, 0.001)
    }

    @Test
    fun handlesZeroLoss() {
        val result =
            PostHarvestLossCalculator.calculate(500.0, 0.0)

        assertEquals(0.0, result.lostQuantityKg, 0.001)
        assertEquals(500.0, result.remainingQuantityKg, 0.001)
    }

    @Test
    fun handlesCompleteLoss() {
        val result =
            PostHarvestLossCalculator.calculate(500.0, 100.0)

        assertEquals(500.0, result.lostQuantityKg, 0.001)
        assertEquals(0.0, result.remainingQuantityKg, 0.001)
    }

    @Test
    fun rejectsLossAboveOneHundredPercent() {
        assertThrows(IllegalArgumentException::class.java) {
            PostHarvestLossCalculator.calculate(500.0, 101.0)
        }
    }
}