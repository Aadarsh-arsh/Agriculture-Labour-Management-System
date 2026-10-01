package com.example.kisanmitra.ui.screens.marketplace

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.kisanmitra.data.SupabaseHarvestedCrop
import com.example.kisanmitra.data.SupabaseHarvestedCropRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HarvestedCropViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository =
        SupabaseHarvestedCropRepository()

    private val _availableCrops =
        MutableStateFlow<List<SupabaseHarvestedCrop>>(emptyList())

    val availableCrops: StateFlow<List<SupabaseHarvestedCrop>> =
        _availableCrops.asStateFlow()

    private val _myCrops =
        MutableStateFlow<List<SupabaseHarvestedCrop>>(emptyList())

    val myCrops: StateFlow<List<SupabaseHarvestedCrop>> =
        _myCrops.asStateFlow()

    private val _errorMessage =
        MutableStateFlow<String?>(null)

    val errorMessage: StateFlow<String?> =
        _errorMessage.asStateFlow()

    private val _isLoading =
        MutableStateFlow(false)

    val isLoading: StateFlow<Boolean> =
        _isLoading.asStateFlow()

    init {
        loadAvailableCrops()
        loadMyCrops()
    }

    fun loadAvailableCrops() {

        viewModelScope.launch {

            _isLoading.value = true
            _errorMessage.value = null

            try {

                _availableCrops.value =
                    repository.getAvailableCrops()

            } catch (e: Exception) {

                _errorMessage.value =
                    e.message ?: "Unable to load harvested crops"

            } finally {

                _isLoading.value = false
            }
        }
    }

    fun loadMyCrops() {

        viewModelScope.launch {

            try {

                _myCrops.value =
                    repository.getMyCrops()

            } catch (e: Exception) {

                _errorMessage.value =
                    e.message ?: "Unable to load your listings"
            }
        }
    }

    fun addHarvestedCrop(
        farmerName: String,
        cropName: String,
        quantity: Double,
        unit: String,
        expectedPrice: Double,
        location: String,
        harvestDate: String,
        description: String,
        contactNumber: String,
        photoUrl: String?,
        onSuccess: () -> Unit = {}
    ) {

        viewModelScope.launch {

            _errorMessage.value = null

            try {

                repository.addHarvestedCrop(
                    farmerName = farmerName,
                    cropName = cropName,
                    quantity = quantity,
                    unit = unit,
                    expectedPrice = expectedPrice,
                    location = location,
                    harvestDate = harvestDate,
                    description = description,
                    contactNumber = contactNumber,
                    photoUrl = photoUrl
                )

                loadAvailableCrops()
                loadMyCrops()

                onSuccess()

            } catch (e: Exception) {

                _errorMessage.value =
                    e.message ?: "Unable to publish harvested crop"
            }
        }
    }

    fun markAsSold(
        cropId: Long
    ) {

        viewModelScope.launch {

            try {

                repository.markAsSold(cropId)

                loadAvailableCrops()
                loadMyCrops()

            } catch (e: Exception) {

                _errorMessage.value =
                    e.message ?: "Unable to update crop status"
            }
        }
    }

    fun deleteHarvestedCrop(
        cropId: Long
    ) {

        viewModelScope.launch {

            try {

                repository.deleteHarvestedCrop(cropId)

                loadAvailableCrops()
                loadMyCrops()

            } catch (e: Exception) {

                _errorMessage.value =
                    e.message ?: "Unable to delete harvested crop"
            }
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }
}