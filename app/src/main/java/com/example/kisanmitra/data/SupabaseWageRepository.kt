package com.example.kisanmitra.data

import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from

class SupabaseWageRepository {

    private val client = SupabaseClientProvider.client

    suspend fun getWages(): List<SupabaseWage> {
        return client
            .from("wages")
            .select()
            .decodeList<SupabaseWage>()
    }

    suspend fun addWage(
        labourId: Long,
        labourName: String,
        dailyWage: Double,
        presentDays: Int
    ) {
        val user =
            client.auth.currentUserOrNull()
                ?: throw IllegalStateException(
                    "User is not logged in"
                )

        val totalWage =
            dailyWage * presentDays

        client.from("wages").insert(
            SupabaseWage(
                user_id = user.id,
                labour_id = labourId,
                labour_name = labourName,
                daily_wage = dailyWage,
                present_days = presentDays,
                total_wage = totalWage
            )
        )
    }

    suspend fun deleteWage(id: Long) {
        client
            .from("wages")
            .delete {
                filter {
                    eq("id", id)
                }
            }
    }
}