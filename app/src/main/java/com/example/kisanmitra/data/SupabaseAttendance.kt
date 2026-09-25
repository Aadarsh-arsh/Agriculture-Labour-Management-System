package com.example.kisanmitra.data

import kotlinx.serialization.Serializable

@Serializable
data class SupabaseAttendance(
    val id: Long? = null,
    val user_id: String,
    val labour_id: Long,
    val task_id: Long,
    val date: String,
    val status: String
)