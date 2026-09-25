package com.example.kisanmitra.ui.screens.labour

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.kisanmitra.data.KisanMitraDatabase
import com.example.kisanmitra.data.Labour
import com.example.kisanmitra.data.LabourRepository
import com.example.kisanmitra.data.SupabaseLabourRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LabourViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val database =
        KisanMitraDatabase.getDatabase(application)

    private val localRepository =
        LabourRepository(database.labourDao())

    private val supabaseRepository =
        SupabaseLabourRepository()

    private val _labourers =
        MutableStateFlow<List<Labour>>(emptyList())

    val labourers: StateFlow<List<Labour>> =
        _labourers.asStateFlow()

    init {
        loadLabourers()
    }

    private fun loadLabourers() {

        viewModelScope.launch {

            try {

                val supabaseLabourers =
                    supabaseRepository.getLabourers()

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

            } catch (e: Exception) {

                Log.e(
                    "LABOUR_SUPABASE",
                    "LOAD FAILED: ${e.message}",
                    e
                )

                localRepository.allLabourers.collect { localLabourers ->
                    _labourers.value = localLabourers
                }
            }
        }
    }

    fun addLabour(
        name: String,
        phone: String,
        wage: String,
        skill: String
    ) {

        val cleanName =
            name.trim()

        val cleanPhone =
            phone.trim()

        val cleanSkill =
            skill.trim()

        val dailyWage =
            wage.trim().toDoubleOrNull()

        if (
            cleanName.isBlank() ||
            cleanPhone.isBlank() ||
            cleanSkill.isBlank()
        ) {
            return
        }

        if (
            dailyWage == null ||
            dailyWage <= 0
        ) {
            return
        }

        viewModelScope.launch {

            try {

                supabaseRepository.addLabour(
                    name = cleanName,
                    phone = cleanPhone,
                    dailyWage = dailyWage,
                    skill = cleanSkill
                )

                Log.d(
                    "LABOUR_SUPABASE",
                    "INSERT SUCCESS"
                )

                loadLabourers()

            } catch (e: Exception) {

                Log.e(
                    "LABOUR_SUPABASE",
                    "INSERT FAILED: ${e.message}",
                    e
                )

                localRepository.insertLabour(
                    Labour(
                        name = cleanName,
                        phone = cleanPhone,
                        dailyWage = dailyWage,
                        skill = cleanSkill
                    )
                )
            }
        }
    }

    fun deleteLabour(
        labour: Labour
    ) {

        viewModelScope.launch {

            try {

                supabaseRepository.deleteLabour(
                    labour.id.toLong()
                )

                Log.d(
                    "LABOUR_SUPABASE",
                    "DELETE SUCCESS"
                )

                loadLabourers()

            } catch (e: Exception) {

                Log.e(
                    "LABOUR_SUPABASE",
                    "DELETE FAILED: ${e.message}",
                    e
                )

                localRepository.deleteLabour(
                    labour
                )
            }
        }
    }
}