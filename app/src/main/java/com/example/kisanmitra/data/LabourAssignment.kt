package com.example.kisanmitra.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "labour_assignments")
data class LabourAssignment(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val labourId: Int,
    val labourName: String,
    val farmName: String,
    val cropName: String,
    val taskName: String,
    val assignmentDate: String
)