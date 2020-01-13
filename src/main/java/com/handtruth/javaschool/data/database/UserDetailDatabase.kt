package com.handtruth.javaschool.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Database
import com.handtruth.javaschool.data.dao.TestDao
import com.handtruth.javaschool.data.dao.UserDetailDao
import com.handtruth.javaschool.data.model.Test
import com.handtruth.javaschool.data.model.UserDetail

@Database(entities = [UserDetail::class], version = 1, exportSchema = false)
abstract class UserDetailDatabase : RoomDatabase() {

    abstract fun userDetailDao(): UserDetailDao

    companion object {
        private var INSTANCE: UserDetailDatabase? = null

        fun getInstance(context: Context): UserDetailDatabase? {
            if (INSTANCE == null) {
                synchronized(UserDetailDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        UserDetailDatabase::class.java, "userDetail_table"
                    )
                        .build()
                }
            }
            return INSTANCE
        }
    }
}