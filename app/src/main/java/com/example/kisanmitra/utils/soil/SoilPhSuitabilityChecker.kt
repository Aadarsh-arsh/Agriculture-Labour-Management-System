package com.example.kisanmitra.utils.soil

enum class SoilPhCategory {
    STRONGLY_ACIDIC,
    ACIDIC,
    NEAR_NEUTRAL,
    ALKALINE,
    STRONGLY_ALKALINE
}

data class SoilPhAssessment(
    val measuredPh: Double,
    val category: SoilPhCategory,
    val suitableForCrop: Boolean
)

object SoilPhSuitabilityChecker {

    fun assess(
        measuredPh: Double,
        minimumSuitablePh: Double,
        maximumSuitablePh: Double
    ): SoilPhAssessment {
        require(measuredPh in 0.0..14.0) {
            "Soil pH must be between 0 and 14."
        }
        require(minimumSuitablePh in 0.0..14.0)
        require(maximumSuitablePh in 0.0..14.0)
        require(minimumSuitablePh <= maximumSuitablePh) {
            "Minimum suitable pH cannot exceed maximum suitable pH."
        }

        val category = when {
            measuredPh < 4.5 -> SoilPhCategory.STRONGLY_ACIDIC
            measuredPh < 6.5 -> SoilPhCategory.ACIDIC
            measuredPh <= 7.5 -> SoilPhCategory.NEAR_NEUTRAL
            measuredPh <= 8.5 -> SoilPhCategory.ALKALINE
            else -> SoilPhCategory.STRONGLY_ALKALINE
        }

        return SoilPhAssessment(
            measuredPh = measuredPh,
            category = category,
            suitableForCrop = measuredPh in minimumSuitablePh..maximumSuitablePh
        )
    }
}
