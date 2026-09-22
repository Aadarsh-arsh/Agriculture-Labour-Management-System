package com.example.kisanmitra.ui.screens.wage

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.kisanmitra.data.Attendance
import com.example.kisanmitra.data.KisanMitraDatabase
import com.example.kisanmitra.data.Labour
import com.example.kisanmitra.data.Wage
import com.example.kisanmitra.data.WageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class WageViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val database =
        KisanMitraDatabase.getDatabase(application)

    private val repository =
        WageRepository(database.wageDao())

    val wages: Flow<List<Wage>> =
        repository.allWages

    val labourers: Flow<List<Labour>> =
        database.labourDao().getAllLabourers()

    val attendance: Flow<List<Attendance>> =
        database.attendanceDao().getAllAttendance()

    fun calculateAndSaveWage(
        labour: Labour,
        attendanceList: List<Attendance>
    ) {

        if (labour.id <= 0 || labour.dailyWage <= 0) {
            return
        }

        val presentDays = attendanceList.count {
            it.labourId == labour.id &&
                    it.status.trim().equals("Present", ignoreCase = true)
        }

        if (presentDays <= 0) {
            return
        }

        val totalWage =
            presentDays * labour.dailyWage

        viewModelScope.launch {

            // Remove the previous wage record for this labourer
            repository.deleteWagesForLabour(labour.id)

            // Save the latest calculated wage
            repository.insertWage(
                Wage(
                    labourId = labour.id,
                    labourName = labour.name.trim(),
                    dailyWage = labour.dailyWage,
                    presentDays = presentDays,
                    totalWage = totalWage
                )
            )
        }
    }

    fun deleteWage(wage: Wage) {
        viewModelScope.launch {
            repository.deleteWage(wage)
        }
    }
}