package com.example.kisanmitra.utils

data class LabourOvertimeResult(
    val regularHours: Double,
    val actualHours: Double,
    val overtimeHours: Double,
    val overtimeRate: Double,
    val overtimePay: Double
)

object LabourOvertimeCalculator {

    fun calculate(
        regularHours: Double,
        actualHours: Double,
        hourlyRate: Double,
        overtimeMultiplier: Double = 1.5
    ): LabourOvertimeResult {
        require(regularHours >= 0.0 && regularHours.isFinite()) {
            "Regular hours must be non-negative and finite"
        }
        require(actualHours >= 0.0 && actualHours.isFinite()) {
            "Actual hours must be non-negative and finite"
        }
        require(hourlyRate >= 0.0 && hourlyRate.isFinite()) {
            "Hourly rate must be non-negative and finite"
        }
        require(overtimeMultiplier > 0.0 && overtimeMultiplier.isFinite()) {
            "Overtime multiplier must be positive and finite"
        }

        val overtimeHours =
            (actualHours - regularHours).coerceAtLeast(0.0)

        val overtimeRate = hourlyRate * overtimeMultiplier
        require(overtimeRate.isFinite()) {
            "Overtime rate exceeds supported numeric range"
        }

        val overtimePay = overtimeHours * overtimeRate
        require(overtimePay.isFinite()) {
            "Overtime pay exceeds supported numeric range"
        }

        return LabourOvertimeResult(
            regularHours = regularHours,
            actualHours = actualHours,
            overtimeHours = overtimeHours,
            overtimeRate = overtimeRate,
            overtimePay = overtimePay
        )
    }
}