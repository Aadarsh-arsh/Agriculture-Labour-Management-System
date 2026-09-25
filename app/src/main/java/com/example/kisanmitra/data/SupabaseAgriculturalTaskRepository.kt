package com.example.kisanmitra.data

import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from

class SupabaseAgriculturalTaskRepository {

    private val client =
        SupabaseClientProvider.client

    private val cropRepository =
        SupabaseCropRepository()

    suspend fun getTasks(): List<SupabaseAgriculturalTask> {

        return client
            .from("agricultural_tasks")
            .select()
            .decodeList<SupabaseAgriculturalTask>()
    }

    suspend fun addTask(
        taskName: String,
        cropName: String,
        taskDate: String,
        status: String
    ) {

        val user =
            client.auth.currentUserOrNull()
                ?: throw IllegalStateException(
                    "User is not logged in"
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

        val cropId =
            crop.id
                ?: throw IllegalStateException(
                    "Crop ID is missing"
                )

        client
            .from("agricultural_tasks")
            .insert(
                SupabaseAgriculturalTask(
                    user_id = user.id,
                    task_name = taskName,
                    crop_id = cropId,
                    task_date =
                        convertDateToSupabaseFormat(
                            taskDate
                        ),
                    status = status
                )
            )
    }

    suspend fun deleteTask(
        id: Long
    ) {

        client
            .from("agricultural_tasks")
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