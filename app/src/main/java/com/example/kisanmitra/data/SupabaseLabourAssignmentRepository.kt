package com.example.kisanmitra.data

import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from

class SupabaseLabourAssignmentRepository {

    private val client =
        SupabaseClientProvider.client

    private val labourRepository =
        SupabaseLabourRepository()

    private val farmRepository =
        SupabaseFarmRepository()

    private val cropRepository =
        SupabaseCropRepository()

    private val taskRepository =
        SupabaseAgriculturalTaskRepository()

    suspend fun getAssignments():
            List<SupabaseLabourAssignment> {

        return client
            .from("labour_assignments")
            .select()
            .decodeList<SupabaseLabourAssignment>()
    }

    suspend fun addAssignment(
        labourName: String,
        farmName: String,
        cropName: String,
        taskName: String,
        assignmentDate: String
    ) {

        val user =
            client.auth.currentUserOrNull()
                ?: throw IllegalStateException(
                    "User is not logged in"
                )

        val labour =
            labourRepository
                .getLabourers()
                .firstOrNull {
                    it.name.equals(
                        labourName.trim(),
                        ignoreCase = true
                    )
                }
                ?: throw IllegalArgumentException(
                    "Labour not found: $labourName"
                )

        val farm =
            farmRepository
                .getFarms()
                .firstOrNull {
                    it.farm_name.equals(
                        farmName.trim(),
                        ignoreCase = true
                    )
                }
                ?: throw IllegalArgumentException(
                    "Farm not found: $farmName"
                )

        val crop =
            cropRepository
                .getCrops()
                .firstOrNull {
                    it.crop_name.equals(
                        cropName.trim(),
                        ignoreCase = true
                    )
                }
                ?: throw IllegalArgumentException(
                    "Crop not found: $cropName"
                )

        val task =
            taskRepository
                .getTasks()
                .firstOrNull {
                    it.task_name.equals(
                        taskName.trim(),
                        ignoreCase = true
                    )
                }
                ?: throw IllegalArgumentException(
                    "Task not found: $taskName"
                )

        val labourId =
            labour.id
                ?: throw IllegalStateException(
                    "Labour ID is missing"
                )

        val farmId =
            farm.id
                ?: throw IllegalStateException(
                    "Farm ID is missing"
                )

        val cropId =
            crop.id
                ?: throw IllegalStateException(
                    "Crop ID is missing"
                )

        val taskId =
            task.id
                ?: throw IllegalStateException(
                    "Task ID is missing"
                )

        client
            .from("labour_assignments")
            .insert(
                SupabaseLabourAssignment(
                    user_id = user.id,
                    labour_id = labourId,
                    farm_id = farmId,
                    crop_id = cropId,
                    task_id = taskId,
                    assignment_date =
                        convertDateToSupabaseFormat(
                            assignmentDate
                        )
                )
            )
    }

    suspend fun deleteAssignment(
        id: Long
    ) {

        client
            .from("labour_assignments")
            .delete {
                filter {
                    eq("id", id)
                }
            }
    }

    private fun convertDateToSupabaseFormat(
        date: String
    ): String {

        val parts = date.split("/")

        if (parts.size != 3) {
            throw IllegalArgumentException(
                "Invalid date format: $date"
            )
        }

        val day = parts[0]
        val month = parts[1]
        val year = parts[2]

        return "$year-$month-$day"
    }
}