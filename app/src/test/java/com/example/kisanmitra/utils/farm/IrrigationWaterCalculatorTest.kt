package com.example.kisanmitra.utils.farm

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class IrrigationWaterCalculatorTest {

    @Test
    fun calculatesWaterAtFullEfficiency() {
        val result =
            IrrigationWaterCalculator.calculate(1.0, 10.0, 100.0)

        assertEquals(100_000.0, result.netWaterLitres, 0.001)
        assertEquals(100_000.0, result.grossWaterLitres, 0.001)
    }

    @Test
    fun accountsForApplicationLosses() {
        val result =
            IrrigationWaterCalculator.calculate(1.0, 10.0, 50.0)

        assertEquals(100_000.0, result.netWaterLitres, 0.001)
        assertEquals(200_000.0, result.grossWaterLitres, 0.001)
    }

    @Test
    fun returnsZeroForZeroArea() {
        val result =
            IrrigationWaterCalculator.calculate(0.0, 10.0, 80.0)

        assertEquals(0.0, result.grossWaterLitres, 0.001)
    }

    @Test
    fun rejectsZeroEfficiency() {
        assertThrows(IllegalArgumentException::class.java) {
            IrrigationWaterCalculator.calculate(1.0, 10.0, 0.0)
        }
    }
}