package com.example.kisanmitra.utils.farm

data class FarmProfitResult(
    val revenue: Double,
    val totalExpenses: Double,
    val netProfit: Double,
    val profitMarginPercent: Double
)

object FarmProfitCalculator {

    fun calculate(
        quantitySoldKg: Double,
        sellingPricePerKg: Double,
        expenses: List<Double>
    ): FarmProfitResult {
        require(quantitySoldKg >= 0.0 &&
            quantitySoldKg.isFinite()) {
            "Quantity sold must be non-negative and finite"
        }
        require(sellingPricePerKg >= 0.0 &&
            sellingPricePerKg.isFinite()) {
            "Selling price must be non-negative and finite"
        }
        require(expenses.all { it >= 0.0 && it.isFinite() }) {
            "Every expense must be non-negative and finite"
        }

        val revenue = quantitySoldKg * sellingPricePerKg
        val totalExpenses = expenses.sum()

        require(revenue.isFinite() && totalExpenses.isFinite()) {
            "Revenue or expenses exceed the supported numeric range"
        }

        val netProfit = revenue - totalExpenses

        require(netProfit.isFinite()) {
            "Net profit exceeds the supported numeric range"
        }

        val margin =
            if (revenue == 0.0) 0.0
            else netProfit / revenue * 100.0

        require(margin.isFinite()) {
            "Profit margin exceeds the supported numeric range"
        }

        return FarmProfitResult(
            revenue = revenue,
            totalExpenses = totalExpenses,
            netProfit = netProfit,
            profitMarginPercent = margin
        )
    }
}