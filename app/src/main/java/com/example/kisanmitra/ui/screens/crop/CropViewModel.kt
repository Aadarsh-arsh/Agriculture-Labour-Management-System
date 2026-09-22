package com.example.kisanmitra.ui.screens.crop

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.kisanmitra.data.Crop
import com.example.kisanmitra.data.CropRepository
import com.example.kisanmitra.data.KisanMitraDatabase
import kotlinx.coroutines.launch

class CropViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val database =
        KisanMitraDatabase.getDatabase(application)

    private val repository =
        CropRepository(database.cropDao())

    val crops = repository.allCrops

    fun addCrop(
        cropName: String,
        farmName: String,
        sowingDate: String,
        cropStage: String
    ) {

        val cleanCropName = cropName.trim()
        val cleanFarmName = farmName.trim()
        val cleanSowingDate = sowingDate.trim()
        val cleanCropStage = cropStage.trim()

        if (
            cleanCropName.isBlank() ||
            cleanFarmName.isBlank() ||
            cleanSowingDate.isBlank() ||
            cleanCropStage.isBlank()
        ) {
            return
        }

        viewModelScope.launch {

            repository.insertCrop(
                Crop(
                    cropName = cleanCropName,
                    farmName = cleanFarmName,
                    sowingDate = cleanSowingDate,
                    cropStage = cleanCropStage
                )
            )
        }
    }

    fun deleteCrop(crop: Crop) {

        viewModelScope.launch {
            repository.deleteCrop(crop)
        }
    }
}