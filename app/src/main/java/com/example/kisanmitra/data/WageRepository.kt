package com.example.kisanmitra.data

import kotlinx.coroutines.flow.Flow

class WageRepository(
    private val wageDao: WageDao
) {

    val allWages: Flow<List<Wage>> =
        wageDao.getAllWages()

    suspend fun insertWage(wage: Wage) {
        wageDao.insertWage(wage)
    }

    suspend fun deleteWage(wage: Wage) {
        wageDao.deleteWage(wage)
    }
}