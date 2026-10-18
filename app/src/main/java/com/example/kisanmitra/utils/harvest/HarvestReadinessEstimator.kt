package com.example.kisanmitra.utils.harvest

enum class HarvestStatus {
    NOT_READY,
    READY,
    OVERDUE
}

data class HarvestReadiness(
    val status: HarvestStatus,
    val daysUntilReady: Int
)

object HarvestReadinessEstimator {

    fun estimate(
        currentDay: Int,
        maturityDay: Int
    ): HarvestReadiness {

        require(currentDay >= 0)
        require(maturityDay >= 0)

        val difference = maturityDay - currentDay

        return when {
            difference > 0 -> HarvestReadiness(
                status = HarvestStatus.NOT_READY,
                daysUntilReady = difference
            )

            difference == 0 -> HarvestReadiness(
                status = HarvestStatus.READY,
                daysUntilReady = 0
            )

            else -> HarvestReadiness(
                status = HarvestStatus.OVERDUE,
                daysUntilReady = 0
            )
        }
    }
}
