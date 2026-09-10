package com.example.kisanmitra.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "crop_stages")
data class CropStage(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val cropName: String,
    val stageName: String,
    val startDate: String,
    val endDate: String,
    val notes: String
)