package com.example.kisanmitra.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "labourers")
data class Labour(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val name: String,

    val phone: String,

    val dailyWage: Double,

    val skill: String
)