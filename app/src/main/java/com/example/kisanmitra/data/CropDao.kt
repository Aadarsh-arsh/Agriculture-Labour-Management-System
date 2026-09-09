package com.example.kisanmitra.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CropDao {

    @Insert
    suspend fun insertCrop(crop: Crop)

    @Delete
    suspend fun deleteCrop(crop: Crop)

    @Query("SELECT * FROM crops ORDER BY cropName ASC")
    fun getAllCrops(): Flow<List<Crop>>
}