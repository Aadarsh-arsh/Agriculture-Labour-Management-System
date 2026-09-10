package com.example.kisanmitra.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        Labour::class,
        Farm::class,
        Crop::class,
        AgriculturalTask::class,
        LabourAssignment::class
    ],
    version = 5,
    exportSchema = false
)
abstract class KisanMitraDatabase : RoomDatabase() {

    abstract fun labourDao(): LabourDao

    abstract fun farmDao(): FarmDao

    abstract fun cropDao(): CropDao

    abstract fun agriculturalTaskDao(): AgriculturalTaskDao

    abstract fun labourAssignmentDao(): LabourAssignmentDao

    companion object {

        @Volatile
        private var INSTANCE: KisanMitraDatabase? = null

        fun getDatabase(context: Context): KisanMitraDatabase {
            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    KisanMitraDatabase::class.java,
                    "kisanmitra_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}