package com.example.kisanmitra.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WageDao {

    @Insert
    suspend fun insertWage(wage: Wage)

    @Delete
    suspend fun deleteWage(wage: Wage)

    @Query("SELECT * FROM wages ORDER BY labourName ASC")
    fun getAllWages(): Flow<List<Wage>>
}