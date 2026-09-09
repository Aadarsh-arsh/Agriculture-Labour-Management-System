package com.example.kisanmitra.data

import kotlinx.coroutines.flow.Flow

class AgriculturalTaskRepository(
    private val taskDao: AgriculturalTaskDao
) {

    val allTasks: Flow<List<AgriculturalTask>> =
        taskDao.getAllTasks()

    suspend fun insertTask(task: AgriculturalTask) {
        taskDao.insertTask(task)
    }

    suspend fun deleteTask(task: AgriculturalTask) {
        taskDao.deleteTask(task)
    }
}