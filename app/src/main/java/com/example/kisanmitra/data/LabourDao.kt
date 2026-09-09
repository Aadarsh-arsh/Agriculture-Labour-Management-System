package com.example.kisanmitra.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LabourDao {

    @Insert
    suspend fun insertLabour(labour: Labour)

    @Delete
    suspend fun deleteLabour(labour: Labour)

    @Query("SELECT * FROM labourers ORDER BY name ASC")
    fun getAllLabourers(): Flow<List<Labour>>
}