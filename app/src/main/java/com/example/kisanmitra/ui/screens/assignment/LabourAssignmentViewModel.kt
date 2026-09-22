package com.example.kisanmitra.ui.screens.assignment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.kisanmitra.data.AgriculturalTask
import com.example.kisanmitra.data.Crop
import com.example.kisanmitra.data.Farm
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
        LabourAssignmentRepository(
            database.labourAssignmentDao()
        )

    val assignments =
        repository.allAssignments

    val labourers: Flow<List<Labour>> =
        database.labourDao().getAllLabourers()

    val farms: Flow<List<Farm>> =
        database.farmDao().getAllFarms()

    val crops: Flow<List<Crop>> =
        database.cropDao().getAllCrops()

    val agriculturalTasks: Flow<List<AgriculturalTask>> =
        database.agriculturalTaskDao().getAllTasks()

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

    fun deleteAssignment(
        assignment: LabourAssignment
    ) {
        viewModelScope.launch {
            repository.deleteAssignment(assignment)
        }
    }
}