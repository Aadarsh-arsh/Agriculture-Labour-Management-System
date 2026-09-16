package com.example.kisanmitra.ui.screens.labour

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.kisanmitra.data.KisanMitraDatabase
import com.example.kisanmitra.data.Labour
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LabourViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val labourDao =
        KisanMitraDatabase
            .getDatabase(application)
            .labourDao()

    val labourers: StateFlow<List<Labour>> =
        labourDao
            .getAllLabourers()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    fun addLabour(labour: Labour) {
        viewModelScope.launch {
            labourDao.insertLabour(labour)
        }
    }

    fun deleteLabour(labour: Labour) {
        viewModelScope.launch {
            labourDao.deleteLabour(labour)
        }
    }
}