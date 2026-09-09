package com.example.kisanmitra.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FarmDao {

    @Insert
    suspend fun insertFarm(farm: Farm)

    @Delete
    suspend fun deleteFarm(farm: Farm)

    @Query("SELECT * FROM farms ORDER BY farmName ASC")
    fun getAllFarms(): Flow<List<Farm>>
}