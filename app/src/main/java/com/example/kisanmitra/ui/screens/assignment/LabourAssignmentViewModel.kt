package com.example.kisanmitra.ui.screens.assignment

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.kisanmitra.data.AgriculturalTask
import com.example.kisanmitra.data.Crop
import com.example.kisanmitra.data.Farm
import com.example.kisanmitra.data.KisanMitraDatabase
import com.example.kisanmitra.data.Labour
import com.example.kisanmitra.data.LabourAssignment
import com.example.kisanmitra.data.LabourAssignmentRepository
import com.example.kisanmitra.data.SupabaseAgriculturalTaskRepository
import com.example.kisanmitra.data.SupabaseCropRepository
import com.example.kisanmitra.data.SupabaseFarmRepository
import com.example.kisanmitra.data.SupabaseLabourAssignmentRepository
import com.example.kisanmitra.data.SupabaseLabourRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LabourAssignmentViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val database =
        KisanMitraDatabase.getDatabase(application)

    private val localRepository =
        LabourAssignmentRepository(
            database.labourAssignmentDao()
        )

    private val supabaseRepository =
        SupabaseLabourAssignmentRepository()

    private val labourRepository =
        SupabaseLabourRepository()

    private val farmRepository =
        SupabaseFarmRepository()

    private val cropRepository =
        SupabaseCropRepository()

    private val taskRepository =
        SupabaseAgriculturalTaskRepository()

    private val _assignments =
        MutableStateFlow<List<LabourAssignment>>(emptyList())

    val assignments: StateFlow<List<LabourAssignment>> =
        _assignments.asStateFlow()

    private val _labourers =
        MutableStateFlow<List<Labour>>(emptyList())

    val labourers: StateFlow<List<Labour>> =
        _labourers.asStateFlow()

    private val _farms =
        MutableStateFlow<List<Farm>>(emptyList())

    val farms: StateFlow<List<Farm>> =
        _farms.asStateFlow()

    private val _crops =
        MutableStateFlow<List<Crop>>(emptyList())

    val crops: StateFlow<List<Crop>> =
        _crops.asStateFlow()

    private val _agriculturalTasks =
        MutableStateFlow<List<AgriculturalTask>>(emptyList())

    val agriculturalTasks: StateFlow<List<AgriculturalTask>> =
        _agriculturalTasks.asStateFlow()

    private val _errorMessage =
        MutableStateFlow<String?>(null)

    val errorMessage: StateFlow<String?> =
        _errorMessage.asStateFlow()

    init {
        loadAllData()
    }

    private fun loadAllData() {
        viewModelScope.launch {
            try {
                val supabaseLabourers =
                    labourRepository.getLabourers()

                val supabaseFarms =
                    farmRepository.getFarms()

                val supabaseCrops =
                    cropRepository.getCrops()

                val supabaseTasks =
                    taskRepository.getTasks()

                _labourers.value =
                    supabaseLabourers.map { labour ->
                        Labour(
                            id = labour.id?.toInt() ?: 0,
                            name = labour.name,
                            phone = labour.phone,
                            dailyWage = labour.daily_wage,
                            skill = labour.skill
                        )
                    }

                _farms.value =
                    supabaseFarms.map { farm ->
                        Farm(
                            id = farm.id?.toInt() ?: 0,
                            farmName = farm.farm_name,
                            location = farm.location,
                            landArea = farm.land_area
                        )
                    }

                _crops.value =
                    supabaseCrops.map { crop ->
                        val farmName =
                            supabaseFarms
                                .firstOrNull {
                                    it.id == crop.farm_id
                                }
                                ?.farm_name
                                ?: "Unknown Farm"

                        Crop(
                            id = crop.id?.toInt() ?: 0,
                            cropName = crop.crop_name,
                            farmName = farmName,
                            sowingDate =
                                convertDateForDisplay(
                                    crop.sowing_date
                                ),
                            cropStage = crop.crop_stage
                        )
                    }

                _agriculturalTasks.value =
                    supabaseTasks.map { task ->
                        val cropName =
                            supabaseCrops
                                .firstOrNull {
                                    it.id == task.crop_id
                                }
                                ?.crop_name
                                ?: "Unknown Crop"

                        AgriculturalTask(
                            id = task.id?.toInt() ?: 0,
                            taskName = task.task_name,
                            cropName = cropName,
                            taskDate =
                                convertDateForDisplay(
                                    task.task_date
                                ),
                            status = task.status
                        )
                    }

                loadAssignments()

            } catch (e: Exception) {
                Log.e(
                    "ASSIGNMENT_SUPABASE",
                    "LOAD FAILED: ${e.message}",
                    e
                )

                localRepository.allAssignments.collect { localAssignments ->
                    _assignments.value = localAssignments
                }
            }
        }
    }

    private fun loadAssignments() {
        viewModelScope.launch {
            try {
                val supabaseAssignments =
                    supabaseRepository.getAssignments()

                val labourers =
                    labourRepository.getLabourers()

                val farms =
                    farmRepository.getFarms()

                val crops =
                    cropRepository.getCrops()

                val tasks =
                    taskRepository.getTasks()

                _assignments.value =
                    supabaseAssignments.map { assignment ->

                        val labourName =
                            labourers
                                .firstOrNull {
                                    it.id == assignment.labour_id
                                }
                                ?.name
                                ?: "Unknown Labour"

                        val farmName =
                            farms
                                .firstOrNull {
                                    it.id == assignment.farm_id
                                }
                                ?.farm_name
                                ?: "Unknown Farm"

                        val cropName =
                            crops
                                .firstOrNull {
                                    it.id == assignment.crop_id
                                }
                                ?.crop_name
                                ?: "Unknown Crop"

                        val taskName =
                            tasks
                                .firstOrNull {
                                    it.id == assignment.task_id
                                }
                                ?.task_name
                                ?: "Unknown Task"

                        LabourAssignment(
                            id =
                                assignment.id
                                    ?.toInt()
                                    ?: 0,
                            labourId =
                                assignment.labour_id.toInt(),
                            labourName = labourName,
                            farmName = farmName,
                            cropName = cropName,
                            taskName = taskName,
                            assignmentDate =
                                convertDateForDisplay(
                                    assignment.assignment_date
                                )
                        )
                    }

            } catch (e: Exception) {
                Log.e(
                    "ASSIGNMENT_SUPABASE",
                    "ASSIGNMENT LOAD FAILED: ${e.message}",
                    e
                )

                localRepository.allAssignments.collect { localAssignments ->
                    _assignments.value = localAssignments
                }
            }
        }
    }

    fun addAssignment(
        labourId: Int,
        labourName: String,
        farmName: String,
        cropName: String,
        taskName: String,
        assignmentDate: String
    ) {
        _errorMessage.value = null

        val cleanLabourName = labourName.trim()
        val cleanFarmName = farmName.trim()
        val cleanCropName = cropName.trim()
        val cleanTaskName = taskName.trim()
        val cleanAssignmentDate = assignmentDate.trim()

        if (labourId <= 0 || cleanLabourName.isBlank()) {
            _errorMessage.value = "Please select a labourer"
            return
        }

        if (cleanFarmName.isBlank()) {
            _errorMessage.value = "Please select a farm"
            return
        }

        if (cleanCropName.isBlank()) {
            _errorMessage.value = "Please select a crop"
            return
        }

        if (cleanTaskName.isBlank()) {
            _errorMessage.value = "Please select a task"
            return
        }

        if (cleanAssignmentDate.isBlank()) {
            _errorMessage.value = "Please enter assignment date"
            return
        }

        viewModelScope.launch {
            try {
                supabaseRepository.addAssignment(
                    labourName = cleanLabourName,
                    farmName = cleanFarmName,
                    cropName = cleanCropName,
                    taskName = cleanTaskName,
                    assignmentDate = cleanAssignmentDate
                )

                Log.d(
                    "ASSIGNMENT_SUPABASE",
                    "INSERT SUCCESS"
                )

                loadAssignments()

            } catch (e: Exception) {
                Log.e(
                    "ASSIGNMENT_SUPABASE",
                    "INSERT FAILED: ${e.message}",
                    e
                )

                try {
                    localRepository.insertAssignment(
                        LabourAssignment(
                            labourId = labourId,
                            labourName = cleanLabourName,
                            farmName = cleanFarmName,
                            cropName = cleanCropName,
                            taskName = cleanTaskName,
                            assignmentDate = cleanAssignmentDate
                        )
                    )
                } catch (localException: Exception) {
                    Log.e(
                        "ASSIGNMENT_SUPABASE",
                        "LOCAL INSERT FAILED: ${localException.message}",
                        localException
                    )

                    _errorMessage.value =
                        "Unable to save assignment"
                }
            }
        }
    }

    fun deleteAssignment(
        assignment: LabourAssignment
    ) {
        _errorMessage.value = null

        viewModelScope.launch {
            try {
                supabaseRepository.deleteAssignment(
                    assignment.id.toLong()
                )

                Log.d(
                    "ASSIGNMENT_SUPABASE",
                    "DELETE SUCCESS"
                )

                loadAssignments()

            } catch (e: Exception) {
                Log.e(
                    "ASSIGNMENT_SUPABASE",
                    "DELETE FAILED: ${e.message}",
                    e
                )

                try {
                    localRepository.deleteAssignment(
                        assignment
                    )
                } catch (localException: Exception) {
                    Log.e(
                        "ASSIGNMENT_SUPABASE",
                        "LOCAL DELETE FAILED: ${localException.message}",
                        localException
                    )

                    _errorMessage.value =
                        "Unable to delete assignment"
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
