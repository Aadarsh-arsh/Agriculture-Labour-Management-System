package com.example.kisanmitra.data

import kotlinx.serialization.Serializable

@Serializable
data class SupabaseAgriculturalTask(
    val id: Long? = null,
    val user_id: String,
    val task_name: String,
    val crop_id: Long,
    val task_date: String,
    val status: String
)