package com.example.kisanmitra.utils.farm

data class IrrigationWaterResult(
    val areaHectares: Double,
    val irrigationDepthMm: Double,
    val efficiencyPercent: Double,
    val netWaterLitres: Double,
    val grossWaterLitres: Double
)

object IrrigationWaterCalculator {

    fun calculate(
        areaHectares: Double,
        irrigationDepthMm: Double,
        efficiencyPercent: Double
    ): IrrigationWaterResult {
        require(areaHectares >= 0.0 && areaHectares.isFinite()) {
            "Area must be non-negative and finite"
        }
        require(irrigationDepthMm >= 0.0 &&
            irrigationDepthMm.isFinite()) {
            "Irrigation depth must be non-negative and finite"
        }
        require(efficiencyPercent > 0.0 &&
            efficiencyPercent <= 100.0 &&
            efficiencyPercent.isFinite()) {
            "Efficiency must be greater than 0 and at most 100"
        }

        // One hectare-mm equals 10,000 litres.
        val netWater = areaHectares * irrigationDepthMm * 10_000.0
        val grossWater = netWater / (efficiencyPercent / 100.0)

        require(netWater.isFinite() && grossWater.isFinite()) {
            "Water requirement exceeds the supported numeric range"
        }

        return IrrigationWaterResult(
            areaHectares = areaHectares,
            irrigationDepthMm = irrigationDepthMm,
            efficiencyPercent = efficiencyPercent,
            netWaterLitres = netWater,
            grossWaterLitres = grossWater
        )
    }
}