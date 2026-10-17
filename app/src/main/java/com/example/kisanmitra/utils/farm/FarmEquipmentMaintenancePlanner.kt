package com.example.kisanmitra.utils.farm

enum class MaintenanceStatus {
    UPCOMING,
    DUE,
    OVERDUE
}

data class MaintenancePlan(
    val equipmentName: String,
    val serviceIntervalDays: Int,
    val daysSinceLastService: Int,
    val daysUntilService: Int,
    val status: MaintenanceStatus
)

object FarmEquipmentMaintenancePlanner {

    fun plan(
        equipmentName: String,
        serviceIntervalDays: Int,
        daysSinceLastService: Int
    ): MaintenancePlan {
        require(equipmentName.isNotBlank()) {
            "Equipment name must not be blank"
        }
        require(serviceIntervalDays > 0) {
            "Service interval must be positive"
        }
        require(daysSinceLastService >= 0) {
            "Days since last service cannot be negative"
        }

        val daysUntilService =
            serviceIntervalDays - daysSinceLastService

        val status = when {
            daysUntilService < 0 -> MaintenanceStatus.OVERDUE
            daysUntilService == 0 -> MaintenanceStatus.DUE
            else -> MaintenanceStatus.UPCOMING
        }

        return MaintenancePlan(
            equipmentName = equipmentName.trim(),
            serviceIntervalDays = serviceIntervalDays,
            daysSinceLastService = daysSinceLastService,
            daysUntilService = daysUntilService,
            status = status
        )
    }
}