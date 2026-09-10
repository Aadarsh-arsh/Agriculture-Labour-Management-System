package com.example.kisanmitra.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CropStageDao {

    @Insert
    suspend fun insertCropStage(cropStage: CropStage)

    @Delete
    suspend fun deleteCropStage(cropStage: CropStage)

    @Query("SELECT * FROM crop_stages ORDER BY startDate ASC")
    fun getAllCropStages(): Flow<List<CropStage>>
}