package com.example.kisanmitra.ui.screens.labour

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.kisanmitra.data.KisanMitraDatabase
import com.example.kisanmitra.data.Labour
import com.example.kisanmitra.data.LabourRepository
import kotlinx.coroutines.launch

class LabourViewModel(application: Application) : AndroidViewModel(application) {

    private val database =
        KisanMitraDatabase.getDatabase(application)

    private val repository =
        LabourRepository(database.labourDao())

    val labourers = repository.allLabourers

    fun addLabour(
        name: String,
        phone: String,
        wage: String,
        skill: String
    ) {

        val cleanName = name.trim()
        val cleanPhone = phone.trim()
        val cleanSkill = skill.trim()
        val dailyWage = wage.trim().toDoubleOrNull()

        // Validate required fields
        if (
            cleanName.isBlank() ||
            cleanPhone.isBlank() ||
            cleanSkill.isBlank()
        ) {
            return
        }

        // Validate wage
        if (dailyWage == null || dailyWage <= 0) {
            return
        }

        viewModelScope.launch {

            repository.insertLabour(
                Labour(
                    name = cleanName,
                    phone = cleanPhone,
                    dailyWage = dailyWage,
                    skill = cleanSkill
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