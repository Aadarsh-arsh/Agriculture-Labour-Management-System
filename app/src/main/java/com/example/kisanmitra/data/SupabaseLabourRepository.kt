package com.example.kisanmitra.data

import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from

class SupabaseLabourRepository {

    private val client =
        SupabaseClientProvider.client

    suspend fun getLabourers(): List<SupabaseLabour> {

        return client
            .from("labourers")
            .select()
            .decodeList<SupabaseLabour>()
    }

    suspend fun addLabour(
        name: String,
        phone: String,
        dailyWage: Double,
        skill: String
    ) {

        val user =
            client.auth.currentUserOrNull()
                ?: throw IllegalStateException(
                    "User is not logged in"
                )

        client
            .from("labourers")
            .insert(
                SupabaseLabour(
                    user_id = user.id,
                    name = name,
                    phone = phone,
                    daily_wage = dailyWage,
                    skill = skill
                )
            )
    }

    suspend fun deleteLabour(
        id: Long
    ) {

        client
            .from("labourers")
            .delete {
                filter {
                    eq("id", id)
                }
            }
    }
}