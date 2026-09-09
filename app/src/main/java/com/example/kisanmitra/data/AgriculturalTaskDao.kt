package com.example.kisanmitra.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AgriculturalTaskDao {

    @Insert
    suspend fun insertTask(task: AgriculturalTask)

    @Delete
    suspend fun deleteTask(task: AgriculturalTask)

    @Query("SELECT * FROM agricultural_tasks ORDER BY taskDate ASC")
    fun getAllTasks(): Flow<List<AgriculturalTask>>
}