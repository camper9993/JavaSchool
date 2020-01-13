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
import com.handtruth.javaschool.data.dao.LessonDao
import com.handtruth.javaschool.data.model.Lesson

@Database(entities = [Lesson::class], version = 1, exportSchema = false)
abstract class LessonDatabase : RoomDatabase() {

    abstract fun lessonDao(): LessonDao

    companion object {
        private var INSTANCE: LessonDatabase? = null

        fun getInstance(context: Context): LessonDatabase? {
            if (INSTANCE == null) {
                synchronized(LessonDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        LessonDatabase::class.java, "lesson_table"
                    )
                        .build()
                }
            }
            return INSTANCE
        }
    }
}