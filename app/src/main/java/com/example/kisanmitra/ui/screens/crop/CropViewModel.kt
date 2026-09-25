package com.example.kisanmitra.ui.screens.crop

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.kisanmitra.data.Crop
import com.example.kisanmitra.data.CropRepository
import com.example.kisanmitra.data.KisanMitraDatabase
import com.example.kisanmitra.data.SupabaseCropRepository
import com.example.kisanmitra.data.SupabaseFarmRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CropViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val database =
        KisanMitraDatabase.getDatabase(application)

    private val localRepository =
        CropRepository(database.cropDao())

    private val supabaseRepository =
        SupabaseCropRepository()

    private val _crops =
        MutableStateFlow<List<Crop>>(emptyList())

    val crops: StateFlow<List<Crop>> =
        _crops.asStateFlow()

    private val _errorMessage =
        MutableStateFlow<String?>(null)

    val errorMessage: StateFlow<String?> =
        _errorMessage.asStateFlow()

    init {
        loadCrops()
    }

    private fun loadCrops() {

        viewModelScope.launch {

            try {

                val supabaseCrops =
                    supabaseRepository.getCrops()

                val farmRepository =
                    SupabaseFarmRepository()

                val farms =
                    farmRepository.getFarms()

                _crops.value =
                    supabaseCrops.map { crop ->

                        val farmName =
                            farms
                                .firstOrNull {
                                    it.id == crop.farm_id
                                }
                                ?.farm_name
                                ?: "Unknown Farm"

                        Crop(
                            id = crop.id?.toInt() ?: 0,
                            cropName = crop.crop_name,
                            farmName = farmName,
                            sowingDate = convertDateForDisplay(
                                crop.sowing_date
                            ),
                            cropStage = crop.crop_stage
                        )
                    }

                _errorMessage.value = null

            } catch (e: Exception) {

                localRepository.allCrops.collect { localCrops ->
                    _crops.value = localCrops
                }
            }
        }
    }

    fun addCrop(
        cropName: String,
        farmName: String,
        sowingDate: String,
        cropStage: String
    ) {

        val cleanCropName =
            cropName.trim()

        val cleanFarmName =
            farmName.trim()

        val cleanSowingDate =
            sowingDate.trim()

        val cleanCropStage =
            cropStage.trim()

        // Crop name validation
        if (cleanCropName.isBlank()) {
            _errorMessage.value =
                "Please enter crop name"
            return
        }

        // Farm validation
        if (cleanFarmName.isBlank()) {
            _errorMessage.value =
                "Please select a farm"
            return
        }

        // Sowing date validation
        if (cleanSowingDate.isBlank()) {
            _errorMessage.value =
                "Please enter sowing date"
            return
        }

        // Crop stage validation
        if (cleanCropStage.isBlank()) {
            _errorMessage.value =
                "Please select crop stage"
            return
        }

        _errorMessage.value = null

        viewModelScope.launch {

            try {

                supabaseRepository.addCrop(
                    farmName = cleanFarmName,
                    cropName = cleanCropName,
                    sowingDate = cleanSowingDate,
                    cropStage = cleanCropStage
                )

                loadCrops()

            } catch (e: Exception) {

                try {

                    localRepository.insertCrop(
                        Crop(
                            cropName = cleanCropName,
                            farmName = cleanFarmName,
                            sowingDate = cleanSowingDate,
                            cropStage = cleanCropStage
                        )
                    )

                    _errorMessage.value = null

                } catch (localError: Exception) {

                    _errorMessage.value =
                        "Unable to save crop"
                }
            }
        }
    }

    fun deleteCrop(
        crop: Crop
    ) {

        viewModelScope.launch {

            try {

                supabaseRepository.deleteCrop(
                    crop.id.toLong()
                )

                loadCrops()

            } catch (e: Exception) {

                try {

                    localRepository.deleteCrop(crop)

                } catch (localError: Exception) {

                    _errorMessage.value =
                        "Unable to delete crop"
                }
            }
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }

    private fun convertDateForDisplay(
        date: String
    ): String {

        val parts = date.split("-")

        if (parts.size != 3) {
            return date
        }

        val year = parts[0]
        val month = parts[1]
        val day = parts[2]

        return "$day/$month/$year"
    }
}