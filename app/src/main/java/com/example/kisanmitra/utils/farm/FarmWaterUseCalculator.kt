
package com.example.kisanmitra.utils.farm

data class FarmWaterUseResult(
    val fieldAreaHectares: Double,
    val netDepthMillimetres: Double,
    val efficiencyPercent: Double,
    val netWaterLitres: Double,
    val grossWaterLitres: Double
)

object FarmWaterUseCalculator {

    fun calculate(
        fieldAreaHectares: Double,
        netDepthMillimetres: Double,
        efficiencyPercent: Double
    ): FarmWaterUseResult {
        require(fieldAreaHectares >= 0.0 && fieldAreaHectares.isFinite()) {
            "Field area must be non-negative and finite"
        }
        require(netDepthMillimetres >= 0.0 && netDepthMillimetres.isFinite()) {
            "Irrigation depth must be non-negative and finite"
        }
        require(
            efficiencyPercent > 0.0 &&
                efficiencyPercent <= 100.0 &&
                efficiencyPercent.isFinite()
        ) {
            "Efficiency must be greater than 0 and at most 100 percent"
        }

        val netLitres = fieldAreaHectares *
            netDepthMillimetres * 10_000.0

        require(netLitres.isFinite()) {
            "Net water volume exceeds supported numeric range"
        }

        val grossLitres = netLitres / (efficiencyPercent / 100.0)

        require(grossLitres.isFinite()) {
            "Gross water volume exceeds supported numeric range"
        }

        return FarmWaterUseResult(
            fieldAreaHectares = fieldAreaHectares,
            netDepthMillimetres = netDepthMillimetres,
            efficiencyPercent = efficiencyPercent,
            netWaterLitres = netLitres,
            grossWaterLitres = grossLitres
        )
    }
}
