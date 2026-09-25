package com.example.kisanmitra.data

import kotlinx.serialization.Serializable

@Serializable
data class SupabaseCropStage(
    val id: Long? = null,
    val user_id: String,
    val crop_id: Long,
    val stage_name: String,
    val start_date: String,
    val end_date: String,
    val notes: String
)