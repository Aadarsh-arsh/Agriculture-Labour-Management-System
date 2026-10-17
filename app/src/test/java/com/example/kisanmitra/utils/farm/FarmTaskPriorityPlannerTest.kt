
package com.example.kisanmitra.utils.farm

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class FarmTaskPriorityPlannerTest {

    @Test
    fun overdueHighImpactTaskIsRankedFirst() {
        val tasks = listOf(
            FarmTask("T1", "Routine inspection", 10, 2),
            FarmTask("T2", "Repair irrigation leak", -1, 5)
        )

        val result = FarmTaskPriorityPlanner.prioritize(tasks)

        assertEquals("T2", result.first().task.id)
        assertEquals(FarmTaskPriority.CRITICAL, result.first().priority)
    }

    @Test
    fun emptyTaskListReturnsEmptyResult() {
        assertEquals(
            emptyList<PrioritizedFarmTask>(),
            FarmTaskPriorityPlanner.prioritize(emptyList())
        )
    }

    @Test
    fun rejectsImpactOutsideSupportedRange() {
        assertThrows(IllegalArgumentException::class.java) {
            FarmTaskPriorityPlanner.prioritize(
                listOf(FarmTask("T1", "Inspect field", 2, 6))
            )
        }
    }

    @Test
    fun rejectsDuplicateTaskIds() {
        assertThrows(IllegalArgumentException::class.java) {
            FarmTaskPriorityPlanner.prioritize(
                listOf(
                    FarmTask("T1", "Water crop", 0, 4),
                    FarmTask("T1", "Inspect crop", 2, 3)
                )
            )
        }
    }
}
