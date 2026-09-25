package com.example.kisanmitra.data

import kotlinx.serialization.Serializable

@Serializable
data class SupabaseFarm(
    val id: Long? = null,
    val user_id: String,
    val farm_name: String,
    val location: String,
    val land_area: Double
)