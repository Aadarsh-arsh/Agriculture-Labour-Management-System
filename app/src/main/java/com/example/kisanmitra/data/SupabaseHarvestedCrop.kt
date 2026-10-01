package com.example.kisanmitra.data

import kotlinx.serialization.Serializable

@Serializable
data class SupabaseHarvestedCrop(
    val id: Long? = null,
    val user_id: String,
    val farmer_name: String,
    val crop_name: String,
    val quantity: Double,
    val unit: String,
    val expected_price: Double,
    val location: String,
    val harvest_date: String,
    val description: String = "",
    val contact_number: String,
    val photo_url: String? = null,
    val status: String = "Available"
)