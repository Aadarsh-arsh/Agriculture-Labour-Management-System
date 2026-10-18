package com.example.kisanmitra.utils.task

data class FarmTask(
    val id: String,
    val name: String,
    val prerequisites: List<String> = emptyList()
)

data class TaskValidationResult(
    val canStart: Boolean,
    val missingPrerequisites: List<String>
)

object FarmTaskDependencyValidator {

    fun validate(
        task: FarmTask,
        completedTaskIds: Set<String>
    ): TaskValidationResult {

        val missing = task.prerequisites
            .filter { it !in completedTaskIds }
            .distinct()

        return TaskValidationResult(
            canStart = missing.isEmpty(),
            missingPrerequisites = missing
        )
    }
}
