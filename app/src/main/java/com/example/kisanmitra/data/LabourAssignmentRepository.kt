package com.example.kisanmitra.data

import kotlinx.coroutines.flow.Flow

class LabourAssignmentRepository(
    private val labourAssignmentDao: LabourAssignmentDao
) {

    val allAssignments: Flow<List<LabourAssignment>> =
        labourAssignmentDao.getAllAssignments()

    suspend fun insertAssignment(assignment: LabourAssignment) {
        labourAssignmentDao.insertAssignment(assignment)
    }

    suspend fun deleteAssignment(assignment: LabourAssignment) {
        labourAssignmentDao.deleteAssignment(assignment)
    }
}