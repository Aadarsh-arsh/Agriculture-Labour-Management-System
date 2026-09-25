package com.example.kisanmitra.data

import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from

class SupabaseCropRepository {

    private val client =
        SupabaseClientProvider.client

    private val farmRepository =
        SupabaseFarmRepository()

    suspend fun getCrops(): List<SupabaseCrop> {

        return client
            .from("crops")
            .select()
            .decodeList<SupabaseCrop>()
    }

    suspend fun addCrop(
        farmName: String,
        cropName: String,
        sowingDate: String,
        cropStage: String
    ) {

        val user =
            client.auth.currentUserOrNull()
                ?: throw IllegalStateException(
                    "User is not logged in"
                )

        // Find farm using the name entered in CropScreen
        val farm =
            farmRepository.getFarmByName(
                farmName.trim()
            )
                ?: throw IllegalArgumentException(
                    "Farm not found: $farmName"
                )

        client
            .from("crops")
            .insert(
                SupabaseCrop(
                    user_id = user.id,
                    farm_id = farm.id
                        ?: throw IllegalStateException(
                            "Farm ID is missing"
                        ),
                    crop_name = cropName,
                    sowing_date =
                        convertDateToSupabaseFormat(
                            sowingDate
                        ),
                    crop_stage = cropStage
                )
            )
    }

    suspend fun deleteCrop(
        id: Long
    ) {

        client
            .from("crops")
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