package com.example.kisanmitra.utils.farm

data class HarvestYieldResult(
    val sampleHarvestKg: Double,
    val sampleAreaSquareMetres: Double,
    val fieldAreaHectares: Double,
    val yieldTonnesPerHectare: Double,
    val estimatedTotalHarvestKg: Double
)

object HarvestYieldEstimator {

    fun calculate(
        sampleHarvestKg: Double,
        sampleAreaSquareMetres: Double,
        fieldAreaHectares: Double
    ): HarvestYieldResult {
        require(sampleHarvestKg >= 0.0 &&
            sampleHarvestKg.isFinite()) {
            "Sample harvest must be non-negative and finite"
        }
        require(sampleAreaSquareMetres > 0.0 &&
            sampleAreaSquareMetres.isFinite()) {
            "Sample area must be positive and finite"
        }
        require(fieldAreaHectares >= 0.0 &&
            fieldAreaHectares.isFinite()) {
            "Field area must be non-negative and finite"
        }

        // 1 hectare = 10,000 square metres.
        // 1 tonne = 1,000 kg.
        val yieldTonnesPerHectare =
            sampleHarvestKg / sampleAreaSquareMetres * 10.0

        val totalHarvestKg =
            sampleHarvestKg / sampleAreaSquareMetres *
                fieldAreaHectares * 10_000.0

        require(yieldTonnesPerHectare.isFinite() &&
            totalHarvestKg.isFinite()) {
            "Estimated yield exceeds the supported numeric range"
        }

        return HarvestYieldResult(
            sampleHarvestKg = sampleHarvestKg,
            sampleAreaSquareMetres = sampleAreaSquareMetres,
            fieldAreaHectares = fieldAreaHectares,
            yieldTonnesPerHectare = yieldTonnesPerHectare,
            estimatedTotalHarvestKg = totalHarvestKg
        )
    }
}