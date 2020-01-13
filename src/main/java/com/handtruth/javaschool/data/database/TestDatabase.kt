package com.handtruth.javaschool.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Database
import com.handtruth.javaschool.data.dao.TestDao
import com.handtruth.javaschool.data.model.Test

@Database(entities = [Test::class], version = 1, exportSchema = false)
abstract class TestDatabase : RoomDatabase() {

    abstract fun testDao(): TestDao

    companion object {
        private var INSTANCE: TestDatabase? = null

        fun getInstance(context: Context): TestDatabase? {
            if (INSTANCE == null) {
                synchronized(TestDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        TestDatabase::class.java, "test_table"
                    )
                        .build()
                }
            }
            return INSTANCE
        }
    }
}