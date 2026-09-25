package com.example.kisanmitra.data

import kotlinx.serialization.Serializable

@Serializable
data class SupabaseCrop(
    val id: Long? = null,
    val user_id: String,
    val farm_id: Long,
    val crop_name: String,
    val sowing_date: String,
    val crop_stage: String
)