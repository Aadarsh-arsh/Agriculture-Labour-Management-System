package com.example.kisanmitra.ui.screens.farm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.kisanmitra.data.Farm
import com.example.kisanmitra.data.FarmRepository
import com.example.kisanmitra.data.KisanMitraDatabase
import kotlinx.coroutines.launch

class FarmViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val database =
        KisanMitraDatabase.getDatabase(application)

    private val repository =
        FarmRepository(database.farmDao())

    val farms = repository.allFarms

    fun addFarm(
        farmName: String,
        location: String,
        landArea: String
    ) {

        val area = landArea.toDoubleOrNull() ?: return

        if (
            farmName.isBlank() ||
            location.isBlank()
        ) {
            return
        }

        viewModelScope.launch {

            repository.insertFarm(
                Farm(
                    farmName = farmName,
                    location = location,
                    landArea = area
                )
            )
        }
    }

    fun deleteFarm(farm: Farm) {

        viewModelScope.launch {
            repository.deleteFarm(farm)
        }
    }
}