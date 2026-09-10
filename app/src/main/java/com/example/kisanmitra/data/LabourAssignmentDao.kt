package com.example.kisanmitra.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LabourAssignmentDao {

    @Insert
    suspend fun insertAssignment(assignment: LabourAssignment)

    @Delete
    suspend fun deleteAssignment(assignment: LabourAssignment)

    @Query("SELECT * FROM labour_assignments ORDER BY assignmentDate ASC")
    fun getAllAssignments(): Flow<List<LabourAssignment>>
}