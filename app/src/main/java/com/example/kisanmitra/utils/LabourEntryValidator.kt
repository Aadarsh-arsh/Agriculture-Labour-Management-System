package com.example.kisanmitra.utils

object LabourEntryValidator {

    fun isValidWorkerName(name: String): Boolean {
        return name.isNotBlank()
    }

    fun isValidDaysWorked(daysWorked: Int): Boolean {
        return daysWorked >= 0
    }

    fun isValidDailyRate(dailyRate: Double): Boolean {
        return dailyRate >= 0.0 && dailyRate.isFinite()
    }
}
