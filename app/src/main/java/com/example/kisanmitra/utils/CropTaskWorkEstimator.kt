
package com.example.kisanmitra.utils

import kotlin.math.ceil

data class CropTaskEstimate(
    val totalWorkerHours: Double,
    val estimatedHours: Double,
    val estimatedDays: Int
)

object CropTaskWorkEstimator {

    fun estimate(
        area: Double,
        productivityPerWorkerHour: Double,
        workerCount: Int,
        hoursPerDay: Double = 8.0
    ): CropTaskEstimate {
        require(area >= 0.0 && area.isFinite()) {
            "Area must be non-negative and finite"
        }
        require(
            productivityPerWorkerHour > 0.0 &&
                productivityPerWorkerHour.isFinite()
        ) {
            "Productivity must be positive and finite"
        }
        require(workerCount > 0) {
            "Worker count must be greater than zero"
        }
        require(hoursPerDay > 0.0 && hoursPerDay.isFinite()) {
            "Hours per day must be positive and finite"
        }

        val totalWorkerHours = area / productivityPerWorkerHour
        val elapsedHours = totalWorkerHours / workerCount

        val estimatedDays = if (area == 0.0) {
            0
        } else {
            ceil(elapsedHours / hoursPerDay).toInt()
        }

        return CropTaskEstimate(
            totalWorkerHours = totalWorkerHours,
            estimatedHours = elapsedHours,
            estimatedDays = estimatedDays
        )
    }
}
