package com.example.kisanmitra.utils

data class LabourShiftCapacityResult(
    val availableWorkerHours: Double,
    val maximumOutput: Double,
    val requiredWorkerHours: Double,
    val canCompleteTarget: Boolean
)

object LabourShiftCapacityPlanner {

    fun plan(
        workerCount: Int,
        shiftHours: Double,
        outputPerWorkerHour: Double,
        targetOutput: Double
    ): LabourShiftCapacityResult {

        require(workerCount > 0) {
            "Worker count must be greater than zero"
        }

        require(shiftHours > 0.0 && shiftHours.isFinite()) {
            "Shift hours must be positive and finite"
        }

        require(
            outputPerWorkerHour > 0.0 &&
                outputPerWorkerHour.isFinite()
        ) {
            "Output per worker hour must be positive and finite"
        }

        require(targetOutput >= 0.0 && targetOutput.isFinite()) {
            "Target output must be non-negative and finite"
        }

        val availableWorkerHours =
            workerCount * shiftHours

        val maximumOutput =
            availableWorkerHours * outputPerWorkerHour

        val requiredWorkerHours =
            targetOutput / outputPerWorkerHour

        return LabourShiftCapacityResult(
            availableWorkerHours = availableWorkerHours,
            maximumOutput = maximumOutput,
            requiredWorkerHours = requiredWorkerHours,
            canCompleteTarget =
                requiredWorkerHours <= availableWorkerHours
        )
    }
}