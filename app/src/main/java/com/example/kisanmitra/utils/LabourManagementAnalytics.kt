
package com.example.kisanmitra.utils

data class LabourRecord(
    val workerName: String,
    val daysWorked: Int,
    val dailyRate: Double
)

data class WorkerLabourSummary(
    val workerName: String,
    val totalDays: Int,
    val totalCost: Double
)

data class LabourAnalyticsSummary(
    val recordCount: Int,
    val totalWorkerDays: Int,
    val totalCost: Double,
    val averageDailyRate: Double,
    val highestCostWorker: WorkerLabourSummary?
)

object LabourManagementAnalytics {

    fun summarize(records: List<LabourRecord>): LabourAnalyticsSummary {
        records.forEach { record ->
            require(record.workerName.isNotBlank()) {
                "Worker name cannot be blank"
            }
            require(record.daysWorked >= 0) {
                "Days worked cannot be negative"
            }
            require(record.dailyRate >= 0.0 && record.dailyRate.isFinite()) {
                "Daily rate must be finite and non-negative"
            }
            require((record.daysWorked.toDouble() * record.dailyRate).isFinite()) {
                "Record cost exceeds supported numeric range"
            }
        }

        val totalDays = records.sumOf { it.daysWorked.toLong() }
        check(totalDays <= Int.MAX_VALUE) {
            "Total worker-days exceed supported numeric range"
        }

        val workerSummaries = records
            .groupBy { it.workerName.trim() }
            .map { (name, workerRecords) ->
                val days = workerRecords.sumOf { it.daysWorked.toLong() }
                check(days <= Int.MAX_VALUE) {
                    "Worker-days exceed supported numeric range"
                }

                val cost = workerRecords.sumOf {
                    it.daysWorked.toDouble() * it.dailyRate
                }
                check(cost.isFinite()) {
                    "Worker cost exceeds supported numeric range"
                }

                WorkerLabourSummary(name, days.toInt(), cost)
            }

        val totalCost = records.sumOf {
            it.daysWorked.toDouble() * it.dailyRate
        }
        check(totalCost.isFinite()) {
            "Total labour cost exceeds supported numeric range"
        }

        return LabourAnalyticsSummary(
            recordCount = records.size,
            totalWorkerDays = totalDays.toInt(),
            totalCost = totalCost,
            averageDailyRate = if (totalDays == 0L) {
                0.0
            } else {
                totalCost / totalDays
            },
            highestCostWorker = workerSummaries.maxByOrNull { it.totalCost }
        )
    }
}
