package com.example.kisanmitra.data

import kotlinx.serialization.Serializable

@Serializable
data class SupabaseLabour(
    val id: Long? = null,
    val user_id: String,
    val name: String,
    val phone: String,
    val daily_wage: Double,
    val skill: String
)