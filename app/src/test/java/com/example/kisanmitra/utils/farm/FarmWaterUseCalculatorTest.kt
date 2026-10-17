
package com.example.kisanmitra.utils.farm

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class FarmWaterUseCalculatorTest {

    @Test
    fun calculatesGrossWaterAtEightyPercentEfficiency() {
        val result = FarmWaterUseCalculator.calculate(
            fieldAreaHectares = 1.0,
            netDepthMillimetres = 10.0,
            efficiencyPercent = 80.0
        )

        assertEquals(100_000.0, result.netWaterLitres, 0.001)
        assertEquals(125_000.0, result.grossWaterLitres, 0.001)
    }

    @Test
    fun supportsZeroFieldArea() {
        val result = FarmWaterUseCalculator.calculate(
            fieldAreaHectares = 0.0,
            netDepthMillimetres = 10.0,
            efficiencyPercent = 80.0
        )

        assertEquals(0.0, result.grossWaterLitres, 0.001)
    }

    @Test
    fun rejectsZeroEfficiency() {
        assertThrows(IllegalArgumentException::class.java) {
            FarmWaterUseCalculator.calculate(1.0, 10.0, 0.0)
        }
    }

    @Test
    fun rejectsNegativeFieldArea() {
        assertThrows(IllegalArgumentException::class.java) {
            FarmWaterUseCalculator.calculate(-1.0, 10.0, 80.0)
        }
    }

    @Test
    fun rejectsEfficiencyAboveOneHundredPercent() {
        assertThrows(IllegalArgumentException::class.java) {
            FarmWaterUseCalculator.calculate(1.0, 10.0, 101.0)
        }
    }

    @Test
    fun rejectsNonFiniteDepth() {
        assertThrows(IllegalArgumentException::class.java) {
            FarmWaterUseCalculator.calculate(
                1.0,
                Double.POSITIVE_INFINITY,
                80.0
            )
        }
    }
}
