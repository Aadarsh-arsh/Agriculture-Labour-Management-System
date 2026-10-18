package com.example.kisanmitra.utils.seeding

data class SeedRequirement(
    val baseSeedKg: Double,
    val adjustedSeedKg: Double
)

object SeedRateCalculator {

    fun calculate(
        areaHectares: Double,
        recommendedKgPerHectare: Double,
        extraPercentage: Double = 0.0
    ): SeedRequirement {
        require(areaHectares.isFinite() && areaHectares > 0.0) {
            "Area must be a positive finite number."
        }
        require(
            recommendedKgPerHectare.isFinite() &&
                recommendedKgPerHectare > 0.0
        ) {
            "Seed rate must be a positive finite number."
        }
        require(extraPercentage.isFinite() && extraPercentage in 0.0..100.0) {
            "Extra percentage must be between 0 and 100."
        }

        val base = areaHectares * recommendedKgPerHectare
        val adjusted = base * (1.0 + extraPercentage / 100.0)

        return SeedRequirement(
            baseSeedKg = base,
            adjustedSeedKg = adjusted
        )
    }
}
