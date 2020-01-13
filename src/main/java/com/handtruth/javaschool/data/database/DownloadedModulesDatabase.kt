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
import com.handtruth.javaschool.data.dao.DownloadedModulesDao
import com.handtruth.javaschool.data.model.DownloadedModules

@Database(entities = [DownloadedModules::class], version = 1, exportSchema = false)
abstract class DownloadedModulesDatabase : RoomDatabase() {

    abstract fun moduleInfoDao(): DownloadedModulesDao

    companion object {
        private var INSTANCE: DownloadedModulesDatabase? = null

        fun getInstance(context: Context): DownloadedModulesDatabase? {
            if (INSTANCE == null) {
                synchronized(DownloadedModulesDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        DownloadedModulesDatabase::class.java, "downloadedModules_table"
                    )
                        .build()
                }
            }
            return INSTANCE
        }
    }
}