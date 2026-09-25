package com.example.kisanmitra.data

import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from

class SupabaseCropStageRepository {

    private val client =
        SupabaseClientProvider.client

    private val cropRepository =
        SupabaseCropRepository()

    suspend fun getCropStages(): List<SupabaseCropStage> {

        return client
            .from("crop_stages")
            .select()
            .decodeList<SupabaseCropStage>()
    }

    suspend fun addCropStage(
        cropName: String,
        stageName: String,
        startDate: String,
        endDate: String,
        notes: String
    ) {

        val user =
            client.auth.currentUserOrNull()
                ?: throw IllegalStateException(
                    "User is not logged in"
                )

        /*
         * Find the Supabase crop using
         * the crop name entered in the UI.
         */
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
            .from("crop_stages")
            .insert(
                SupabaseCropStage(
                    user_id = user.id,
                    crop_id = cropId,
                    stage_name = stageName,
                    start_date =
                        convertDateToSupabaseFormat(
                            startDate
                        ),
                    end_date =
                        convertDateToSupabaseFormat(
                            endDate
                        ),
                    notes = notes
                )
            )
    }

    suspend fun deleteCropStage(
        id: Long
    ) {

        client
            .from("crop_stages")
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