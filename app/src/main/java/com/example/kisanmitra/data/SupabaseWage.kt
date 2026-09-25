package com.example.kisanmitra.data

import kotlinx.serialization.Serializable

@Serializable
data class SupabaseWage(
    val id: Long? = null,
    val user_id: String,
    val labour_id: Long,
    val labour_name: String,
    val daily_wage: Double,
    val present_days: Int,
    val total_wage: Double
)