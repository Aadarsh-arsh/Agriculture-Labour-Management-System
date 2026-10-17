package com.example.kisanmitra.utils.farm

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class FarmProfitCalculatorTest {

    @Test
    fun calculatesRevenueExpensesAndProfit() {
        val result = FarmProfitCalculator.calculate(
            1_000.0,
            30.0,
            listOf(8_000.0, 5_000.0, 2_000.0)
        )

        assertEquals(30_000.0, result.revenue, 0.001)
        assertEquals(15_000.0, result.totalExpenses, 0.001)
        assertEquals(15_000.0, result.netProfit, 0.001)
        assertEquals(50.0, result.profitMarginPercent, 0.001)
    }

    @Test
    fun identifiesFarmLoss() {
        val result =
            FarmProfitCalculator.calculate(100.0, 20.0, listOf(3_000.0))

        assertEquals(-1_000.0, result.netProfit, 0.001)
        assertEquals(-50.0, result.profitMarginPercent, 0.001)
    }

    @Test
    fun handlesZeroRevenue() {
        val result =
            FarmProfitCalculator.calculate(0.0, 30.0, listOf(500.0))

        assertEquals(-500.0, result.netProfit, 0.001)
        assertEquals(0.0, result.profitMarginPercent, 0.001)
    }

    @Test
    fun rejectsNegativeExpenses() {
        assertThrows(IllegalArgumentException::class.java) {
            FarmProfitCalculator.calculate(
                100.0, 20.0, listOf(-100.0)
            )
        }
    }
}