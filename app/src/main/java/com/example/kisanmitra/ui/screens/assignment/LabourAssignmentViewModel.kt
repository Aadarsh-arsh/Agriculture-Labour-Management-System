package com.example.kisanmitra.ui.screens.assignment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.kisanmitra.data.KisanMitraDatabase
import com.example.kisanmitra.data.Labour
import com.example.kisanmitra.data.LabourAssignment
import com.example.kisanmitra.data.LabourAssignmentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class LabourAssignmentViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val database =
        KisanMitraDatabase.getDatabase(application)

    private val repository =
        LabourAssignmentRepository(database.labourAssignmentDao())

    val assignments = repository.allAssignments

    // Registered labourers from Room database
    val labourers: Flow<List<Labour>> =
        database.labourDao().getAllLabourers()

    fun addAssignment(
        labourId: Int,
        labourName: String,
        farmName: String,
        cropName: String,
        taskName: String,
        assignmentDate: String
    ) {

        val cleanLabourName = labourName.trim()
        val cleanFarmName = farmName.trim()
        val cleanCropName = cropName.trim()
        val cleanTaskName = taskName.trim()
        val cleanAssignmentDate = assignmentDate.trim()

        if (
            labourId <= 0 ||
            cleanLabourName.isBlank() ||
            cleanFarmName.isBlank() ||
            cleanCropName.isBlank() ||
            cleanTaskName.isBlank() ||
            cleanAssignmentDate.isBlank()
        ) {
            return
        }

        viewModelScope.launch {

            repository.insertAssignment(
                LabourAssignment(
                    labourId = labourId,
                    labourName = cleanLabourName,
                    farmName = cleanFarmName,
                    cropName = cleanCropName,
                    taskName = cleanTaskName,
                    assignmentDate = cleanAssignmentDate
                )
            )
        }
    }

    fun deleteAssignment(assignment: LabourAssignment) {

        viewModelScope.launch {
            repository.deleteAssignment(assignment)
        }
    }
}