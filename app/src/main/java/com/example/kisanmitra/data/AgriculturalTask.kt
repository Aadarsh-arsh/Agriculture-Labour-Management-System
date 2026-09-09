package com.example.kisanmitra.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "agricultural_tasks")
data class AgriculturalTask(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val taskName: String,

    val cropName: String,

    val taskDate: String,

    val status: String
)