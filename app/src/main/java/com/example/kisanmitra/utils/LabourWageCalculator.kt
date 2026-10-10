package com.example.kisanmitra.utils

object LabourWageCalculator {

    fun calculateWage(daysWorked: Int, dailyRate: Double): Double {
        require(daysWorked >= 0) {
            "Days worked cannot be negative"
        }
        require(dailyRate >= 0.0 && dailyRate.isFinite()) {
            "Daily rate must be finite and non-negative"
        }

        return daysWorked * dailyRate
    }
}
