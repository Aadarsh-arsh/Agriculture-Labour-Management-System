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

        if (
            cropName.isBlank() ||
            farmName.isBlank() ||
            sowingDate.isBlank() ||
            cropStage.isBlank()
        ) {
            return
        }

        viewModelScope.launch {

            repository.insertCrop(
                Crop(
                    cropName = cropName,
                    farmName = farmName,
                    sowingDate = sowingDate,
                    cropStage = cropStage
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