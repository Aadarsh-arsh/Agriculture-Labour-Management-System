package com.example.kisanmitra.data

import kotlinx.serialization.Serializable

@Serializable
data class SupabaseLabourAssignment(
    val id: Long? = null,
    val user_id: String,
    val labour_id: Long,
    val farm_id: Long,
    val crop_id: Long,
    val task_id: Long,
    val assignment_date: String
)