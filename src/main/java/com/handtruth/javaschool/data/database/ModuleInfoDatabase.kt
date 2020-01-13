package com.handtruth.javaschool.data.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import androidx.room.RoomDatabase
import com.handtruth.javaschool.data.dao.ModuleInfoDao
import com.handtruth.javaschool.data.model.ModuleInfo
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.annotation.NonNull
import android.icu.lang.UCharacter.GraphemeClusterBreak.V
import okhttp3.internal.Internal.instance
import android.os.AsyncTask
import android.icu.lang.UCharacter.GraphemeClusterBreak.V
import android.provider.ContactsContract
import androidx.room.Database
import com.handtruth.javaschool.R

@Database(entities = [ModuleInfo::class], version = 1, exportSchema = false)
abstract class ModuleInfoDatabase : RoomDatabase() {

    abstract fun moduleInfoDao(): ModuleInfoDao

    companion object {
        private var INSTANCE: ModuleInfoDatabase? = null

        fun getInstance(context: Context): ModuleInfoDatabase? {
            if (INSTANCE == null) {
                synchronized(ModuleInfoDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        ModuleInfoDatabase::class.java, "moduleInfo_table"
                    )
                        .build()
                }
            }
            return INSTANCE
        }
    }
}