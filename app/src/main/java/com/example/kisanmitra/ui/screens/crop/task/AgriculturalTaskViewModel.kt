package com.example.kisanmitra.ui.screens.task

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.kisanmitra.data.AgriculturalTask
import com.example.kisanmitra.data.AgriculturalTaskRepository
import com.example.kisanmitra.data.KisanMitraDatabase
import kotlinx.coroutines.launch

class AgriculturalTaskViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val database =
        KisanMitraDatabase.getDatabase(application)

    private val repository =
        AgriculturalTaskRepository(
            database.agriculturalTaskDao()
        )

    val tasks = repository.allTasks

    fun addTask(
        taskName: String,
        cropName: String,
        taskDate: String,
        status: String
    ) {

        if (
            taskName.isBlank() ||
            cropName.isBlank() ||
            taskDate.isBlank() ||
            status.isBlank()
        ) {
            return
        }

        viewModelScope.launch {

            repository.insertTask(
                AgriculturalTask(
                    taskName = taskName,
                    cropName = cropName,
                    taskDate = taskDate,
                    status = status
                )
            )
        }
    }

    fun deleteTask(task: AgriculturalTask) {

        viewModelScope.launch {
            repository.deleteTask(task)
        }
    }
}