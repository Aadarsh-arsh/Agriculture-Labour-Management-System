package com.example.kisanmitra.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "attendance")
data class Attendance(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val labourId: Int,
    val labourName: String,
    val date: String,
    val status: String,
    val taskName: String
)