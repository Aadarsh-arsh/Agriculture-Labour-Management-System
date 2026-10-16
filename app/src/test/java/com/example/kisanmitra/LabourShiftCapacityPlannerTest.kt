package com.example.kisanmitra

import com.example.kisanmitra.utils.LabourShiftCapacityPlanner
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class LabourShiftCapacityPlannerTest {

    @Test
    fun calculatesTeamCapacity() {
        val result = LabourShiftCapacityPlanner.plan(
            workerCount = 5,
            shiftHours = 8.0,
            outputPerWorkerHour = 2.0,
            targetOutput = 70.0
        )

        assertEquals(40.0, result.availableWorkerHours, 0.001)
        assertEquals(80.0, result.maximumOutput, 0.001)
        assertEquals(35.0, result.requiredWorkerHours, 0.001)
        assertTrue(result.canCompleteTarget)
    }

    @Test
    fun detectsUnachievableTarget() {
        val result = LabourShiftCapacityPlanner.plan(
            workerCount = 2,
            shiftHours = 4.0,
            outputPerWorkerHour = 1.5,
            targetOutput = 20.0
        )

        assertEquals(12.0, result.maximumOutput, 0.001)
        assertFalse(result.canCompleteTarget)
    }

    @Test(expected = IllegalArgumentException::class)
    fun rejectsZeroShiftHours() {
        LabourShiftCapacityPlanner.plan(
            workerCount = 2,
            shiftHours = 0.0,
            outputPerWorkerHour = 1.0,
            targetOutput = 5.0
        )
    }

    @Test
    fun acceptsZeroTarget() {
        val result = LabourShiftCapacityPlanner.plan(
            workerCount = 2,
            shiftHours = 8.0,
            outputPerWorkerHour = 1.0,
            targetOutput = 0.0
        )

        assertTrue(result.canCompleteTarget)
        assertEquals(0.0, result.requiredWorkerHours, 0.001)
    }

    @Test(expected = IllegalArgumentException::class)
    fun rejectsZeroWorkerCount() {
        LabourShiftCapacityPlanner.plan(
            workerCount = 0,
            shiftHours = 8.0,
            outputPerWorkerHour = 1.0,
            targetOutput = 5.0
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun rejectsNegativeTarget() {
        LabourShiftCapacityPlanner.plan(
            workerCount = 2,
            shiftHours = 8.0,
            outputPerWorkerHour = 1.0,
            targetOutput = -1.0
        )
    }

    @Test
    fun exactCapacityCanCompleteTarget() {
        val result = LabourShiftCapacityPlanner.plan(
            workerCount = 2,
            shiftHours = 4.0,
            outputPerWorkerHour = 2.0,
            targetOutput = 16.0
        )

        assertEquals(16.0, result.maximumOutput, 0.001)
        assertEquals(8.0, result.requiredWorkerHours, 0.001)
        assertTrue(result.canCompleteTarget)
    }
}