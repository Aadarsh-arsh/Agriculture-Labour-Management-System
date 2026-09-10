package com.example.kisanmitra.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wages")
data class Wage(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val labourId: Int,
    val labourName: String,
    val dailyWage: Double,
    val presentDays: Int,
    val totalWage: Double
)