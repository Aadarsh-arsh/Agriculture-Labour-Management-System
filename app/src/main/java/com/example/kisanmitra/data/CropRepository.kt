package com.example.kisanmitra.data

import kotlinx.coroutines.flow.Flow

class CropRepository(
    private val cropDao: CropDao
) {

    val allCrops: Flow<List<Crop>> =
        cropDao.getAllCrops()

    suspend fun insertCrop(crop: Crop) {
        cropDao.insertCrop(crop)
    }

    suspend fun deleteCrop(crop: Crop) {
        cropDao.deleteCrop(crop)
    }
}