
package com.example.kisanmitra.utils.farm

data class FarmTask(
    val id: String,
    val title: String,
    val daysUntilDue: Int,
    val impact: Int
)

enum class FarmTaskPriority {
    CRITICAL,
    HIGH,
    MEDIUM,
    LOW
}

data class PrioritizedFarmTask(
    val task: FarmTask,
    val priority: FarmTaskPriority,
    val score: Int
)

object FarmTaskPriorityPlanner {

    fun prioritize(tasks: List<FarmTask>): List<PrioritizedFarmTask> {
        require(tasks.map { it.id }.distinct().size == tasks.size) {
            "Task IDs must be unique"
        }

        tasks.forEach { task ->
            require(task.id.isNotBlank()) {
                "Task ID must not be blank"
            }
            require(task.title.isNotBlank()) {
                "Task title must not be blank"
            }
            require(task.impact in 1..5) {
                "Impact must be between 1 and 5"
            }
        }

        return tasks.map { task ->
            val urgency = when {
                task.daysUntilDue < 0 -> 5
                task.daysUntilDue == 0 -> 5
                task.daysUntilDue <= 2 -> 4
                task.daysUntilDue <= 7 -> 3
                task.daysUntilDue <= 14 -> 2
                else -> 1
            }

            val score = urgency * 10 + task.impact * 5

            val priority = when {
                score >= 70 -> FarmTaskPriority.CRITICAL
                score >= 55 -> FarmTaskPriority.HIGH
                score >= 40 -> FarmTaskPriority.MEDIUM
                else -> FarmTaskPriority.LOW
            }

            PrioritizedFarmTask(
                task = task,
                priority = priority,
                score = score
            )
        }.sortedWith(
            compareByDescending<PrioritizedFarmTask> { it.score }
                .thenBy { it.task.daysUntilDue }
                .thenBy { it.task.id }
        )
    }
}
