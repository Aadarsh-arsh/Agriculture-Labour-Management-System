package com.example.kisanmitra.ui.screens.crop

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.kisanmitra.data.CropStage
import com.example.kisanmitra.data.CropStageRepository
import com.example.kisanmitra.data.KisanMitraDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class CropStageViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val database =
        KisanMitraDatabase.getDatabase(application)

    private val repository =
        CropStageRepository(database.cropStageDao())

    val cropStages: Flow<List<CropStage>> =
        repository.allCropStages

    fun addCropStage(
        cropName: String,
        stageName: String,
        startDate: String,
        endDate: String,
        notes: String
    ) {
        if (
            cropName.isBlank() ||
            stageName.isBlank() ||
            startDate.isBlank() ||
            endDate.isBlank()
        ) {
            return
        }

        viewModelScope.launch {
            repository.insertCropStage(
                CropStage(
                    cropName = cropName,
                    stageName = stageName,
                    startDate = startDate,
                    endDate = endDate,
                    notes = notes
                )
            )
        }
    }

    fun deleteCropStage(cropStage: CropStage) {
        viewModelScope.launch {
            repository.deleteCropStage(cropStage)
        }
    }
}