package com.handtruth.javaschool.data.repo

import android.app.Application
import androidx.lifecycle.LiveData
import com.handtruth.javaschool.data.model.ModuleInfo
import android.os.AsyncTask
import com.handtruth.javaschool.data.dao.LessonDao
import com.handtruth.javaschool.data.dao.ModuleInfoDao
import com.handtruth.javaschool.data.database.LessonDatabase
import com.handtruth.javaschool.data.model.Lesson
import com.handtruth.javaschool.data.model.Test


class LessonsRepo (val application : Application? = null) {

    private val allLesson: LiveData<List<Lesson>>
    private val lessonDao: LessonDao


    init {
        val lessonDatabase : LessonDatabase = application?.let {
            LessonDatabase.getInstance(
                it
            )
        }!!
        lessonDao = lessonDatabase.lessonDao()
        allLesson = lessonDao.getAllModuleInfo()
    }

    fun insertOrUpdate(lesson: Lesson) {
        val list = allLesson.value
        if (list.isNullOrEmpty())
            insert(lesson)
        else {
            list.forEach {
                if (it.id == lesson.id) {
                    update(lesson)
                    return
                } else
                    insert(lesson)
            }
        }
    }

    fun getLessonsByid (id : Int) : LiveData<List<Lesson>> {
        return lessonDao.getLessonsByID(id)
    }

    fun insertOrUpdate(lessons: List<Lesson>) {
        val list = allLesson.value
        lessons.forEach { lesson ->
            if (list.isNullOrEmpty())
                insert(lesson)
            else {
                list.forEach {
                    if (it.id == lesson.id) {
                        update(lesson)
                    } else
                        insert(lesson)
                }
            }
        }
    }

    fun insert(lessons: List<Lesson>) {
        for (item in lessons)
            InsertLessonAsyncTask(lessonDao).execute(item)
    }


    fun insert(lesson: Lesson) {
        InsertLessonAsyncTask(lessonDao).execute(lesson)
    }

    fun update(lesson: Lesson) {
        UpdateLessonAsyncTask(lessonDao).execute(lesson)
    }

    fun delete(lesson: Lesson) {
        DeleteLessonAsyncTask(lessonDao).execute(lesson)
    }

    fun delete(id: Int) {
        DeleteLessonAsyncTaskById(
            lessonDao
        ).execute(id)
//        lessonDao.delete(id)
    }

    fun deleteAllModules() {
        DeleteAllLessonAsyncTask(lessonDao)
            .execute()
    }

    fun getAllModuleInfo(): LiveData<List<Lesson>> {
        return allLesson
    }

    fun getContent(idLesson: Int) : LiveData<Lesson>{
        return lessonDao.getContent(idLesson)
    }

//    fun getAllDownloaded(): LiveData<List<ModuleInfo>> {
//        return lessonDao.getAllDownloaded()
//    }


    private class InsertLessonAsyncTask(private val lessonDao: LessonDao) :
        AsyncTask<Lesson, Void, Void>() {

        override fun doInBackground(vararg lesson: Lesson): Void? {
            lessonDao.insert(lesson[0])
            return null
        }
    }

    private class UpdateLessonAsyncTask(private val lessonDao: LessonDao) :
        AsyncTask<Lesson, Void, Void>() {
        override fun doInBackground(vararg lesson: Lesson): Void? {
            lessonDao.update(lesson[0])
            return null
        }
    }

    private class DeleteLessonAsyncTask (private val lessonDao: LessonDao) :
        AsyncTask<Lesson, Void, Void>() {

        override fun doInBackground(vararg lesson: Lesson): Void? {
            lessonDao.delete(lesson[0])
            return null
        }
    }

    private class DeleteLessonAsyncTaskById (private val lessonDao: LessonDao) :
        AsyncTask<Int, Void, Int>() {

        override fun doInBackground(vararg id: Int?): Int? {
            return id[0]
        }

        override fun onPostExecute(result: Int?) {
            DeleteLessonAsyncTaskById(
                lessonDao
            ).execute(result!!)
//            lessonDao.delete(result!!)
        }
    }

    private class DeleteAllLessonAsyncTask (private val lessonDao: LessonDao) :
        AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg voids: Void): Void? {
            lessonDao.deleteAll()
            return null
        }
    }

}