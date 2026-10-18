package com.example.kisanmitra.utils.equipment

import java.time.LocalDate
import java.time.temporal.ChronoUnit

enum class MaintenanceStatus {
    UPCOMING,
    DUE_TODAY,
    OVERDUE
}

data class MaintenanceAssessment(
    val equipmentName: String,
    val nextServiceDate: LocalDate,
    val status: MaintenanceStatus,
    val daysUntilService: Long
)

object EquipmentMaintenanceTracker {

    fun assess(
        equipmentName: String,
        lastServiceDate: LocalDate,
        serviceIntervalDays: Long,
        today: LocalDate
    ): MaintenanceAssessment {
        require(equipmentName.isNotBlank()) {
            "Equipment name cannot be blank."
        }
        require(serviceIntervalDays > 0) {
            "Service interval must be positive."
        }
        require(!lastServiceDate.isAfter(today)) {
            "Last service date cannot be in the future."
        }

        val nextServiceDate = lastServiceDate.plusDays(serviceIntervalDays)
        val daysUntilService = ChronoUnit.DAYS.between(today, nextServiceDate)

        val status = when {
            daysUntilService > 0 -> MaintenanceStatus.UPCOMING
            daysUntilService == 0L -> MaintenanceStatus.DUE_TODAY
            else -> MaintenanceStatus.OVERDUE
        }

        return MaintenanceAssessment(
            equipmentName = equipmentName.trim(),
            nextServiceDate = nextServiceDate,
            status = status,
            daysUntilService = daysUntilService
        )
    }
}
