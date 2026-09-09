package com.example.kisanmitra.data

import kotlinx.coroutines.flow.Flow

class FarmRepository(
    private val farmDao: FarmDao
) {

    val allFarms: Flow<List<Farm>> =
        farmDao.getAllFarms()

    suspend fun insertFarm(farm: Farm) {
        farmDao.insertFarm(farm)
    }

    suspend fun deleteFarm(farm: Farm) {
        farmDao.deleteFarm(farm)
    }
}