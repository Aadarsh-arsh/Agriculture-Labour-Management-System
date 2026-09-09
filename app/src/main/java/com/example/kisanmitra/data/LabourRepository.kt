package com.example.kisanmitra.data

import kotlinx.coroutines.flow.Flow

class LabourRepository(
    private val labourDao: LabourDao
) {

    val allLabourers: Flow<List<Labour>> =
        labourDao.getAllLabourers()

    suspend fun insertLabour(labour: Labour) {
        labourDao.insertLabour(labour)
    }

    suspend fun deleteLabour(labour: Labour) {
        labourDao.deleteLabour(labour)
    }
}