
package com.example.kisanmitra.utils

import kotlin.math.ceil

object LabourDemandForecaster {

    fun forecast(
        historicalWorkerCounts: List<Int>,
        periodsToAverage: Int = 3
    ): Int {
        require(historicalWorkerCounts.isNotEmpty()) {
            "Historical worker counts cannot be empty"
        }
        require(periodsToAverage > 0) {
            "Periods to average must be positive"
        }
        require(historicalWorkerCounts.all { it >= 0 }) {
            "Worker counts cannot be negative"
        }

        val recentCounts =
            historicalWorkerCounts.takeLast(periodsToAverage)

        return ceil(recentCounts.average()).toInt()
    }
}
