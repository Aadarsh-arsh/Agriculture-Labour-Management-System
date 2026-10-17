package com.example.kisanmitra.utils.farm

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class FarmEquipmentMaintenancePlannerTest {

    @Test
    fun identifiesUpcomingMaintenance() {
        val result =
            FarmEquipmentMaintenancePlanner.plan("Tractor", 30, 20)

        assertEquals(10, result.daysUntilService)
        assertEquals(MaintenanceStatus.UPCOMING, result.status)
    }

    @Test
    fun identifiesMaintenanceDueToday() {
        val result =
            FarmEquipmentMaintenancePlanner.plan("Water Pump", 30, 30)

        assertEquals(0, result.daysUntilService)
        assertEquals(MaintenanceStatus.DUE, result.status)
    }

    @Test
    fun identifiesOverdueMaintenance() {
        val result =
            FarmEquipmentMaintenancePlanner.plan("Sprayer", 30, 35)

        assertEquals(-5, result.daysUntilService)
        assertEquals(MaintenanceStatus.OVERDUE, result.status)
    }

    @Test
    fun rejectsInvalidServiceInterval() {
        assertThrows(IllegalArgumentException::class.java) {
            FarmEquipmentMaintenancePlanner.plan("Tractor", 0, 10)
        }
    }
}