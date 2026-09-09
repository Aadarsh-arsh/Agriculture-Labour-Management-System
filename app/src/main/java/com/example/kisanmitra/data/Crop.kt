package com.example.kisanmitra.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "crops")
data class Crop(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val cropName: String,

    val farmName: String,

    val sowingDate: String,

    val cropStage: String
)