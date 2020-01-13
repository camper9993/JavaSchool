package com.handtruth.javaschool.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.handtruth.javaschool.data.model.ModuleInfo
import androidx.room.Database
import com.handtruth.javaschool.data.dao.ModuleTestsDao
import com.handtruth.javaschool.data.model.ModuleTests

@Database(entities = [ModuleTests::class], version = 1, exportSchema = false)
abstract class ModuleTestDatabase : RoomDatabase() {

    abstract fun moduleTestDao(): ModuleTestsDao

    companion object {
        private var INSTANCE: ModuleTestDatabase? = null

        fun getInstance(context: Context): ModuleTestDatabase? {
            if (INSTANCE == null) {
                synchronized(ModuleTestDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        ModuleTestDatabase::class.java, "moduleTest_table"
                    )
                        .build()
                }
            }
            return INSTANCE
        }
    }
}