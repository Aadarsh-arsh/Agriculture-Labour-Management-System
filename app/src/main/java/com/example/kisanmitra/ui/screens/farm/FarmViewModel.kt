package com.example.kisanmitra.ui.screens.farm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.kisanmitra.data.Farm
import com.example.kisanmitra.data.FarmRepository
import com.example.kisanmitra.data.KisanMitraDatabase
import com.example.kisanmitra.data.SupabaseFarmRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FarmViewModel(
    application: Application
) : AndroidViewModel(application) {

    // Local Room database
    private val database =
        KisanMitraDatabase.getDatabase(application)

    private val localRepository =
        FarmRepository(database.farmDao())

    // Supabase repository
    private val supabaseRepository =
        SupabaseFarmRepository()

    private val _farms =
        MutableStateFlow<List<Farm>>(emptyList())

    val farms: StateFlow<List<Farm>> =
        _farms.asStateFlow()

    // Error message for validation / operation failures
    private val _errorMessage =
        MutableStateFlow<String?>(null)

    val errorMessage: StateFlow<String?> =
        _errorMessage.asStateFlow()

    init {
        loadFarms()
    }

    private fun loadFarms() {

        viewModelScope.launch {

            try {

                val supabaseFarms =
                    supabaseRepository.getFarms()

                _farms.value = supabaseFarms.map { farm ->

                    Farm(
                        id = farm.id?.toInt() ?: 0,
                        farmName = farm.farm_name,
                        location = farm.location,
                        landArea = farm.land_area
                    )
                }

                _errorMessage.value = null

            } catch (e: Exception) {

                // If Supabase fails, use local Room data
                localRepository.allFarms.collect { localFarms ->
                    _farms.value = localFarms
                }
            }
        }
    }

    fun addFarm(
        farmName: String,
        location: String,
        landArea: String
    ) {

        val cleanFarmName = farmName.trim()
        val cleanLocation = location.trim()
        val area = landArea.trim().toDoubleOrNull()

        // Validate farm name
        if (cleanFarmName.isBlank()) {
            _errorMessage.value = "Please enter farm name"
            return
        }

        // Validate location
        if (cleanLocation.isBlank()) {
            _errorMessage.value = "Please enter farm location"
            return
        }

        // Validate land area
        if (area == null) {
            _errorMessage.value = "Please enter a valid land area"
            return
        }

        if (area <= 0) {
            _errorMessage.value = "Land area must be greater than 0"
            return
        }

        _errorMessage.value = null

        viewModelScope.launch {

            try {

                // Add to Supabase
                supabaseRepository.addFarm(
                    farmName = cleanFarmName,
                    location = cleanLocation,
                    landArea = area
                )

                // Reload farms from Supabase
                loadFarms()

            } catch (e: Exception) {

                // If Supabase fails, save locally
                try {

                    localRepository.insertFarm(
                        Farm(
                            farmName = cleanFarmName,
                            location = cleanLocation,
                            landArea = area
                        )
                    )

                    _errorMessage.value = null

                } catch (localError: Exception) {

                    _errorMessage.value =
                        "Unable to save farm"
                }
            }
        }
    }

    fun deleteFarm(farm: Farm) {

        viewModelScope.launch {

            try {

                // Delete from Supabase
                supabaseRepository.deleteFarm(
                    farm.id.toLong()
                )

                // Reload after deletion
                loadFarms()

            } catch (e: Exception) {

                // If Supabase fails, delete locally
                try {

                    localRepository.deleteFarm(farm)

                } catch (localError: Exception) {

                    _errorMessage.value =
                        "Unable to delete farm"
                }
            }
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }
}