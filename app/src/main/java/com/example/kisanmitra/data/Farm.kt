package com.example.kisanmitra.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "farms")
data class Farm(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val farmName: String,

    val location: String,

    val landArea: Double
)