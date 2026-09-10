package com.example.kisanmitra.ui.screens.assignment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.kisanmitra.data.KisanMitraDatabase
import com.example.kisanmitra.data.LabourAssignment
import com.example.kisanmitra.data.LabourAssignmentRepository
import kotlinx.coroutines.launch

class LabourAssignmentViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val database = KisanMitraDatabase.getDatabase(application)

    private val repository =
        LabourAssignmentRepository(database.labourAssignmentDao())

    val assignments = repository.allAssignments

    fun addAssignment(
        labourId: Int,
        labourName: String,
        farmName: String,
        cropName: String,
        taskName: String,
        assignmentDate: String
    ) {

        if (
            labourName.isBlank() ||
            farmName.isBlank() ||
            cropName.isBlank() ||
            taskName.isBlank() ||
            assignmentDate.isBlank()
        ) {
            return
        }

        viewModelScope.launch {

            repository.insertAssignment(
                LabourAssignment(
                    labourId = labourId,
                    labourName = labourName,
                    farmName = farmName,
                    cropName = cropName,
                    taskName = taskName,
                    assignmentDate = assignmentDate
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