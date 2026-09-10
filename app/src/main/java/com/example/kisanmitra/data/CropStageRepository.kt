package com.example.kisanmitra.data

import kotlinx.coroutines.flow.Flow

class CropStageRepository(
    private val cropStageDao: CropStageDao
) {

    val allCropStages: Flow<List<CropStage>> =
        cropStageDao.getAllCropStages()

    suspend fun insertCropStage(cropStage: CropStage) {
        cropStageDao.insertCropStage(cropStage)
    }

    suspend fun deleteCropStage(cropStage: CropStage) {
        cropStageDao.deleteCropStage(cropStage)
    }
}