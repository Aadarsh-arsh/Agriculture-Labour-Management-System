package com.example.kisanmitra.data


import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from

class SupabaseHarvestedCropRepository {

    private val client =
        SupabaseClientProvider.client

    suspend fun getAvailableCrops(): List<SupabaseHarvestedCrop> {

        return client
            .from("harvested_crops")
            .select()
            .decodeList<SupabaseHarvestedCrop>()
            .filter {
                it.status == "Available"
            }
    }

    suspend fun getMyCrops(): List<SupabaseHarvestedCrop> {

        val user =
            client.auth.currentUserOrNull()
                ?: throw IllegalStateException(
                    "User is not logged in"
                )

        return client
            .from("harvested_crops")
            .select()
            .decodeList<SupabaseHarvestedCrop>()
            .filter {
                it.user_id == user.id
            }
    }

    suspend fun addHarvestedCrop(
        farmerName: String,
        cropName: String,
        quantity: Double,
        unit: String,
        expectedPrice: Double,
        location: String,
        harvestDate: String,
        description: String,
        contactNumber: String,
        photoUrl: String?
    ) {

        val user =
            client.auth.currentUserOrNull()
                ?: throw IllegalStateException(
                    "User is not logged in"
                )

        client
            .from("harvested_crops")
            .insert(
                SupabaseHarvestedCrop(
                    user_id = user.id,
                    farmer_name = farmerName,
                    crop_name = cropName,
                    quantity = quantity,
                    unit = unit,
                    expected_price = expectedPrice,
                    location = location,
                    harvest_date = harvestDate,
                    description = description,
                    contact_number = contactNumber,
                    photo_url = photoUrl,
                    status = "Available"
                )
            )
    }

    suspend fun markAsSold(
        cropId: Long
    ) {

        client
            .from("harvested_crops")
            .update(
                {
                    set(
                        "status",
                        "Sold"
                    )
                }
            ) {
                filter {
                    eq(
                        "id",
                        cropId
                    )
                }
            }
    }

    suspend fun deleteHarvestedCrop(
        cropId: Long
    ) {

        client
            .from("harvested_crops")
            .delete {
                filter {
                    eq(
                        "id",
                        cropId
                    )
                }
            }
    }
}