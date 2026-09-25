package com.example.kisanmitra.ui.screens.crop

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.kisanmitra.data.CropStage
import com.example.kisanmitra.data.CropStageRepository
import com.example.kisanmitra.data.KisanMitraDatabase
import com.example.kisanmitra.data.SupabaseCropRepository
import com.example.kisanmitra.data.SupabaseCropStageRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CropStageViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val database =
        KisanMitraDatabase.getDatabase(application)

    private val localRepository =
        CropStageRepository(database.cropStageDao())

    private val supabaseRepository =
        SupabaseCropStageRepository()

    private val cropRepository =
        SupabaseCropRepository()

    private val _cropStages =
        MutableStateFlow<List<CropStage>>(emptyList())

    val cropStages: StateFlow<List<CropStage>> =
        _cropStages.asStateFlow()

    private val _errorMessage =
        MutableStateFlow<String?>(null)

    val errorMessage: StateFlow<String?> =
        _errorMessage.asStateFlow()

    init {
        loadCropStages()
    }

    private fun loadCropStages() {

        viewModelScope.launch {

            try {

                val supabaseStages =
                    supabaseRepository.getCropStages()

                val crops =
                    cropRepository.getCrops()

                _cropStages.value =
                    supabaseStages.map { stage ->

                        val cropName =
                            crops
                                .firstOrNull {
                                    it.id == stage.crop_id
                                }
                                ?.crop_name
                                ?: "Unknown Crop"

                        CropStage(
                            id = stage.id?.toInt() ?: 0,
                            cropName = cropName,
                            stageName = stage.stage_name,
                            startDate =
                                convertDateForDisplay(
                                    stage.start_date
                                ),
                            endDate =
                                convertDateForDisplay(
                                    stage.end_date
                                ),
                            notes = stage.notes
                        )
                    }

                _errorMessage.value = null

            } catch (e: Exception) {

                Log.e(
                    "CROP_STAGE_SUPABASE",
                    "LOAD FAILED: ${e.message}",
                    e
                )

                localRepository.allCropStages.collect { localStages ->
                    _cropStages.value = localStages
                }
            }
        }
    }

    fun addCropStage(
        cropName: String,
        stageName: String,
        startDate: String,
        endDate: String,
        notes: String
    ) {

        val cleanCropName =
            cropName.trim()

        val cleanStageName =
            stageName.trim()

        val cleanStartDate =
            startDate.trim()

        val cleanEndDate =
            endDate.trim()

        val cleanNotes =
            notes.trim()

        // Crop validation
        if (cleanCropName.isBlank()) {
            _errorMessage.value =
                "Please enter crop name"
            return
        }

        // Stage validation
        if (cleanStageName.isBlank()) {
            _errorMessage.value =
                "Please enter stage name"
            return
        }

        // Start date validation
        if (cleanStartDate.isBlank()) {
            _errorMessage.value =
                "Please enter start date"
            return
        }

        // End date validation
        if (cleanEndDate.isBlank()) {
            _errorMessage.value =
                "Please enter end date"
            return
        }

        _errorMessage.value = null

        viewModelScope.launch {

            try {

                supabaseRepository.addCropStage(
                    cropName = cleanCropName,
                    stageName = cleanStageName,
                    startDate = cleanStartDate,
                    endDate = cleanEndDate,
                    notes = cleanNotes
                )

                Log.d(
                    "CROP_STAGE_SUPABASE",
                    "INSERT SUCCESS"
                )

                loadCropStages()

            } catch (e: Exception) {

                Log.e(
                    "CROP_STAGE_SUPABASE",
                    "INSERT FAILED: ${e.message}",
                    e
                )

                try {

                    // Fallback to local Room database
                    localRepository.insertCropStage(
                        CropStage(
                            cropName = cleanCropName,
                            stageName = cleanStageName,
                            startDate = cleanStartDate,
                            endDate = cleanEndDate,
                            notes = cleanNotes
                        )
                    )

                    _errorMessage.value = null

                } catch (localError: Exception) {

                    _errorMessage.value =
                        "Unable to save crop stage"
                }
            }
        }
    }

    fun deleteCropStage(
        cropStage: CropStage
    ) {

        viewModelScope.launch {

            try {

                supabaseRepository.deleteCropStage(
                    cropStage.id.toLong()
                )

                Log.d(
                    "CROP_STAGE_SUPABASE",
                    "DELETE SUCCESS"
                )

                loadCropStages()

            } catch (e: Exception) {

                Log.e(
                    "CROP_STAGE_SUPABASE",
                    "DELETE FAILED: ${e.message}",
                    e
                )

                try {

                    // Fallback to local Room database
                    localRepository.deleteCropStage(
                        cropStage
                    )

                } catch (localError: Exception) {

                    _errorMessage.value =
                        "Unable to delete crop stage"
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