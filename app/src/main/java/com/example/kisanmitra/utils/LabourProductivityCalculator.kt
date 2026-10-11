
package com.example.kisanmitra.utils

data class LabourProductivityResult(
    val totalOutput: Double,
    val totalWorkerHours: Double,
    val outputPerWorkerHour: Double,
    val workerHoursPerOutputUnit: Double
)

object LabourProductivityCalculator {

    fun calculate(
        completedOutput: Double,
        workerCount: Int,
        hoursPerWorker: Double
    ): LabourProductivityResult {
        require(completedOutput >= 0.0) {
            "Completed output cannot be negative"
        }
        require(workerCount > 0) {
            "Worker count must be greater than zero"
        }
        require(hoursPerWorker > 0.0 && hoursPerWorker.isFinite()) {
            "Hours per worker must be positive and finite"
        }
        require(completedOutput.isFinite()) {
            "Completed output must be finite"
        }

        val totalHours = workerCount * hoursPerWorker
        val productivity = completedOutput / totalHours
        val hoursPerUnit =
            if (completedOutput == 0.0) 0.0
            else totalHours / completedOutput

        return LabourProductivityResult(
            totalOutput = completedOutput,
            totalWorkerHours = totalHours,
            outputPerWorkerHour = productivity,
            workerHoursPerOutputUnit = hoursPerUnit
        )
    }
}
