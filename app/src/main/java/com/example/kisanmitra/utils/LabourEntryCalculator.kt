package com.example.kisanmitra.utils

data class LabourEntryResult(
    val workerName: String,
    val daysWorked: Int,
    val dailyRate: Double,
    val totalWage: Double
)

object LabourEntryCalculator {

    fun calculate(
        workerName: String,
        daysWorked: Int,
        dailyRate: Double
    ): LabourEntryResult {
        require(LabourEntryValidator.isValidWorkerName(workerName)) {
            "Worker name cannot be blank"
        }
        require(LabourEntryValidator.isValidDaysWorked(daysWorked)) {
            "Days worked cannot be negative"
        }
        require(LabourEntryValidator.isValidDailyRate(dailyRate)) {
            "Daily rate must be finite and non-negative"
        }

        val totalWage =
            LabourWageCalculator.calculateWage(daysWorked, dailyRate)

        return LabourEntryResult(
            workerName = workerName.trim(),
            daysWorked = daysWorked,
            dailyRate = dailyRate,
            totalWage = totalWage
        )
    }
}