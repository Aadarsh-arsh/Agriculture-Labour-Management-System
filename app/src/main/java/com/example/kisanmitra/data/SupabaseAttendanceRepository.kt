package com.example.kisanmitra.data

import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from

class SupabaseAttendanceRepository {

    private val client =
        SupabaseClientProvider.client

    private val labourRepository =
        SupabaseLabourRepository()

    private val taskRepository =
        SupabaseAgriculturalTaskRepository()

    suspend fun getAttendance():
            List<SupabaseAttendance> {

        return client
            .from("attendance")
            .select()
            .decodeList<SupabaseAttendance>()
    }

    suspend fun addAttendance(
        labourName: String,
        taskName: String,
        date: String,
        status: String
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

        val taskId =
            task.id
                ?: throw IllegalStateException(
                    "Task ID is missing"
                )

        client
            .from("attendance")
            .insert(
                SupabaseAttendance(
                    user_id = user.id,
                    labour_id = labourId,
                    task_id = taskId,
                    date =
                        convertDateToSupabaseFormat(
                            date
                        ),
                    status = status
                )
            )
    }

    suspend fun deleteAttendance(
        id: Long
    ) {

        client
            .from("attendance")
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