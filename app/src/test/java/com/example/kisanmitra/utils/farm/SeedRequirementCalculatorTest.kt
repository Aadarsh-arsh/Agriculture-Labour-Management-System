package com.example.kisanmitra.utils.farm

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class SeedRequirementCalculatorTest {

    @Test
    fun calculatesBasicSeedRequirement() {
        val result =
            SeedRequirementCalculator.calculate(2.0, 20.0)

        assertEquals(40.0, result.baseSeedKg, 0.001)
        assertEquals(40.0, result.adjustedSeedKg, 0.001)
    }

    @Test
    fun adjustsForGerminationRateAndExtraSeed() {
        val result = SeedRequirementCalculator.calculate(
            fieldAreaHectares = 1.0,
            seedRateKgPerHectare = 20.0,
            germinationPercent = 80.0,
            extraSeedPercent = 10.0
        )

        assertEquals(20.0, result.baseSeedKg, 0.001)
        assertEquals(27.5, result.adjustedSeedKg, 0.001)
    }

    @Test
    fun supportsZeroFieldArea() {
        val result =
            SeedRequirementCalculator.calculate(0.0, 20.0)

        assertEquals(0.0, result.adjustedSeedKg, 0.001)
    }

    @Test
    fun rejectsZeroGerminationRate() {
        assertThrows(IllegalArgumentException::class.java) {
            SeedRequirementCalculator.calculate(
                1.0, 20.0, germinationPercent = 0.0
            )
        }
    }
}