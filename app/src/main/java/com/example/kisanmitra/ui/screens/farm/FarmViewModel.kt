package com.example.kisanmitra.ui.screens.farm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.kisanmitra.data.Farm
import com.example.kisanmitra.data.FarmRepository
import com.example.kisanmitra.data.KisanMitraDatabase
import com.example.kisanmitra.data.SupabaseFarm
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

            } catch (e: Exception) {

                // If Supabase fails, keep using local Room data
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

        // Validate required fields
        if (
            cleanFarmName.isBlank() ||
            cleanLocation.isBlank()
        ) {
            return
        }

        // Validate land area
        if (area == null || area <= 0) {
            return
        }

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
                localRepository.insertFarm(
                    Farm(
                        farmName = cleanFarmName,
                        location = cleanLocation,
                        landArea = area
                    )
                )
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
                localRepository.deleteFarm(farm)
            }
        }
    }
}