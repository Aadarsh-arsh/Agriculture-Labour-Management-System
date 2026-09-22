package com.example.kisanmitra.ui.screens.labour

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.kisanmitra.data.KisanMitraDatabase
import com.example.kisanmitra.data.Labour
import com.example.kisanmitra.data.LabourRepository
import kotlinx.coroutines.launch

class LabourViewModel(application: Application) : AndroidViewModel(application) {

    private val database = KisanMitraDatabase.getDatabase(application)

    private val repository = LabourRepository(
        database.labourDao()
    )

    val labourers = repository.allLabourers

    fun addLabour(
        name: String,
        phone: String,
        wage: String,
        skill: String
    ) {
        val dailyWage = wage.toDoubleOrNull() ?: return

        if (
            name.isBlank() ||
            phone.isBlank() ||
            skill.isBlank()
        ) {
            return
        }

        viewModelScope.launch {
            repository.insertLabour(
                Labour(
                    name = name,
                    phone = phone,
                    dailyWage = dailyWage,
                    skill = skill
                )
            )
        }
    }

    fun deleteLabour(labour: Labour) {
        viewModelScope.launch {
            repository.deleteLabour(labour)
        }
    }
}