package com.example.kisanmitra.utils.farm

data class SeedRequirementResult(
    val fieldAreaHectares: Double,
    val seedRateKgPerHectare: Double,
    val germinationPercent: Double,
    val extraSeedPercent: Double,
    val baseSeedKg: Double,
    val adjustedSeedKg: Double
)

object SeedRequirementCalculator {

    fun calculate(
        fieldAreaHectares: Double,
        seedRateKgPerHectare: Double,
        germinationPercent: Double = 100.0,
        extraSeedPercent: Double = 0.0
    ): SeedRequirementResult {
        require(fieldAreaHectares >= 0.0 &&
            fieldAreaHectares.isFinite()) {
            "Field area must be non-negative and finite"
        }
        require(seedRateKgPerHectare >= 0.0 &&
            seedRateKgPerHectare.isFinite()) {
            "Seed rate must be non-negative and finite"
        }
        require(germinationPercent > 0.0 &&
            germinationPercent <= 100.0 &&
            germinationPercent.isFinite()) {
            "Germination must be greater than 0 and at most 100"
        }
        require(extraSeedPercent >= 0.0 &&
            extraSeedPercent.isFinite()) {
            "Extra seed percentage must be non-negative and finite"
        }

        val baseSeed =
            fieldAreaHectares * seedRateKgPerHectare

        val germinationAdjusted =
            baseSeed / (germinationPercent / 100.0)

        val adjustedSeed =
            germinationAdjusted * (1.0 + extraSeedPercent / 100.0)

        require(baseSeed.isFinite() &&
            adjustedSeed.isFinite()) {
            "Seed requirement exceeds the supported numeric range"
        }

        return SeedRequirementResult(
            fieldAreaHectares = fieldAreaHectares,
            seedRateKgPerHectare = seedRateKgPerHectare,
            germinationPercent = germinationPercent,
            extraSeedPercent = extraSeedPercent,
            baseSeedKg = baseSeed,
            adjustedSeedKg = adjustedSeed
        )
    }
}