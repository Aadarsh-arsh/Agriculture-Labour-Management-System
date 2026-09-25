package com.example.kisanmitra.data

import com.example.kisanmitra.data.SupabaseClientProvider
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from

class SupabaseFarmRepository {

    private val client =
        SupabaseClientProvider.client

    suspend fun getFarms(): List<SupabaseFarm> {

        return client
            .from("farms")
            .select()
            .decodeList<SupabaseFarm>()
    }

    suspend fun getFarmByName(
        farmName: String
    ): SupabaseFarm? {

        return client
            .from("farms")
            .select {
                filter {
                    eq("farm_name", farmName)
                }
            }
            .decodeList<SupabaseFarm>()
            .firstOrNull()
    }

    suspend fun addFarm(
        farmName: String,
        location: String,
        landArea: Double
    ) {

        val user =
            client.auth.currentUserOrNull()
                ?: throw IllegalStateException(
                    "User is not logged in"
                )

        client.from("farms").insert(
            SupabaseFarm(
                user_id = user.id,
                farm_name = farmName,
                location = location,
                land_area = landArea
            )
        )
    }

    suspend fun deleteFarm(
        id: Long
    ) {

        client
            .from("farms")
            .delete {
                filter {
                    eq("id", id)
                }
            }
    }
}