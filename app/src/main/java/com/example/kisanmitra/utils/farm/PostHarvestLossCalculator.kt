package com.example.kisanmitra.utils.farm

data class PostHarvestLossResult(
    val originalQuantityKg: Double,
    val lossPercent: Double,
    val lostQuantityKg: Double,
    val remainingQuantityKg: Double
)

object PostHarvestLossCalculator {

    fun calculate(
        originalQuantityKg: Double,
        lossPercent: Double
    ): PostHarvestLossResult {
        require(originalQuantityKg >= 0.0 &&
            originalQuantityKg.isFinite()) {
            "Original quantity must be non-negative and finite"
        }
        require(lossPercent in 0.0..100.0 &&
            lossPercent.isFinite()) {
            "Loss percentage must be between 0 and 100"
        }

        val lostQuantity =
            originalQuantityKg * lossPercent / 100.0

        val remainingQuantity =
            originalQuantityKg - lostQuantity

        require(lostQuantity.isFinite() &&
            remainingQuantity.isFinite()) {
            "Calculated quantities exceed the supported numeric range"
        }

        return PostHarvestLossResult(
            originalQuantityKg = originalQuantityKg,
            lossPercent = lossPercent,
            lostQuantityKg = lostQuantity,
            remainingQuantityKg = remainingQuantity
        )
    }
}