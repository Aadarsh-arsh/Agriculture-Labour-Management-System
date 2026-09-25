package com.example.kisanmitra.ui.screens.task

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.kisanmitra.data.AgriculturalTask
import com.example.kisanmitra.data.AgriculturalTaskRepository
import com.example.kisanmitra.data.KisanMitraDatabase
import com.example.kisanmitra.data.SupabaseAgriculturalTaskRepository
import com.example.kisanmitra.data.SupabaseCropRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AgriculturalTaskViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val database =
        KisanMitraDatabase.getDatabase(application)

    private val localRepository =
        AgriculturalTaskRepository(
            database.agriculturalTaskDao()
        )

    private val supabaseRepository =
        SupabaseAgriculturalTaskRepository()

    private val cropRepository =
        SupabaseCropRepository()

    private val _tasks =
        MutableStateFlow<List<AgriculturalTask>>(emptyList())

    val tasks: StateFlow<List<AgriculturalTask>> =
        _tasks.asStateFlow()

    private val _errorMessage =
        MutableStateFlow<String?>(null)

    val errorMessage: StateFlow<String?> =
        _errorMessage.asStateFlow()

    init {
        loadTasks()
    }

    private fun loadTasks() {

        viewModelScope.launch {

            try {

                val supabaseTasks =
                    supabaseRepository.getTasks()

                val crops =
                    cropRepository.getCrops()

                _tasks.value =
                    supabaseTasks.map { task ->

                        val cropName =
                            crops
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

            } catch (e: Exception) {

                Log.e(
                    "TASK_SUPABASE",
                    "LOAD FAILED: ${e.message}",
                    e
                )

                localRepository.allTasks.collect { localTasks ->
                    _tasks.value = localTasks
                }
            }
        }
    }

    fun addTask(
        taskName: String,
        cropName: String,
        taskDate: String,
        status: String
    ) {

        _errorMessage.value = null

        val cleanTaskName =
            taskName.trim()

        val cleanCropName =
            cropName.trim()

        val cleanTaskDate =
            taskDate.trim()

        val cleanStatus =
            status.trim()

        if (cleanTaskName.isBlank()) {

            _errorMessage.value =
                "Please enter task name"

            return
        }

        if (cleanCropName.isBlank()) {

            _errorMessage.value =
                "Please select a crop"

            return
        }

        if (cleanTaskDate.isBlank()) {

            _errorMessage.value =
                "Please enter task date"

            return
        }

        if (cleanStatus.isBlank()) {

            _errorMessage.value =
                "Please select task status"

            return
        }

        viewModelScope.launch {

            try {

                supabaseRepository.addTask(
                    taskName = cleanTaskName,
                    cropName = cleanCropName,
                    taskDate = cleanTaskDate,
                    status = cleanStatus
                )

                Log.d(
                    "TASK_SUPABASE",
                    "INSERT SUCCESS"
                )

                loadTasks()

            } catch (e: Exception) {

                Log.e(
                    "TASK_SUPABASE",
                    "INSERT FAILED: ${e.message}",
                    e
                )

                try {

                    localRepository.insertTask(
                        AgriculturalTask(
                            taskName = cleanTaskName,
                            cropName = cleanCropName,
                            taskDate = cleanTaskDate,
                            status = cleanStatus
                        )
                    )

                } catch (localException: Exception) {

                    Log.e(
                        "TASK_SUPABASE",
                        "LOCAL INSERT FAILED: ${localException.message}",
                        localException
                    )

                    _errorMessage.value =
                        "Unable to save task"
                }
            }
        }
    }

    fun deleteTask(
        task: AgriculturalTask
    ) {

        _errorMessage.value = null

        viewModelScope.launch {

            try {

                supabaseRepository.deleteTask(
                    task.id.toLong()
                )

                Log.d(
                    "TASK_SUPABASE",
                    "DELETE SUCCESS"
                )

                loadTasks()

            } catch (e: Exception) {

                Log.e(
                    "TASK_SUPABASE",
                    "DELETE FAILED: ${e.message}",
                    e
                )

                try {

                    localRepository.deleteTask(task)

                } catch (localException: Exception) {

                    Log.e(
                        "TASK_SUPABASE",
                        "LOCAL DELETE FAILED: ${localException.message}",
                        localException
                    )

                    _errorMessage.value =
                        "Unable to delete task"
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