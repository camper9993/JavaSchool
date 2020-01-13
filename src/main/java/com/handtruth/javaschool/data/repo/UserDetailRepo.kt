package com.handtruth.javaschool.data.repo


import android.app.Application
import androidx.lifecycle.LiveData
import com.handtruth.javaschool.data.model.ModuleInfo
import android.os.AsyncTask
import com.handtruth.javaschool.data.dao.LessonDao
import com.handtruth.javaschool.data.dao.ModuleInfoDao
import com.handtruth.javaschool.data.dao.UserDetailDao
import com.handtruth.javaschool.data.database.LessonDatabase
import com.handtruth.javaschool.data.database.UserDetailDatabase
import com.handtruth.javaschool.data.model.Lesson
import com.handtruth.javaschool.data.model.Test
import com.handtruth.javaschool.data.model.UserDetail


class UserDetailRepo (val application : Application? = null) {

    private val allUserDetail: LiveData<List<UserDetail>>
    private val userDetailDao: UserDetailDao


    init {
        val userDetailDatabase : UserDetailDatabase = application?.let {
            UserDetailDatabase.getInstance(
                it
            )
        }!!
        userDetailDao = userDetailDatabase.userDetailDao()
        allUserDetail = userDetailDao.getAllTests()
    }

    fun insertOrUpdate(userDetail: UserDetail) {
        val list = allUserDetail.value
        if (list.isNullOrEmpty())
            insert(userDetail)
        else {
            list.forEach {
                if (it.login == userDetail.login) {
                    update(userDetail)
                    return
                }
                else
                    insert(userDetail)
            }
        }
    }


    fun insertOrUpdate(userDetail: List<UserDetail>) {
        val list = allUserDetail.value
        userDetail.forEach { lesson ->
            if (list.isNullOrEmpty())
                insert(lesson)
            else {
                list.forEach {
                    if (it.login == lesson.login) {
                        update(lesson)
                    } else insert(lesson)
                }
            }
        }
    }

    fun insert(userDetails: List<UserDetail>) {
        for (item in userDetails)
            InsertLessonAsyncTask(userDetailDao).execute(item)
    }


    fun insert(userDetail: UserDetail) {
        InsertLessonAsyncTask(userDetailDao).execute(userDetail)
    }

    fun update(userDetail: UserDetail) {
        UpdateLessonAsyncTask(userDetailDao).execute(userDetail)
    }

    fun delete(userDetail: UserDetail) {
        DeleteLessonAsyncTask(userDetailDao).execute(userDetail)
    }

    fun delete(login: String) {
        DeleteLessonAsyncTaskById(userDetailDao).execute(login)
    }

    fun deleteAll() {
        userDetailDao.deleteAll()
    }

    fun getAllUser(): LiveData<List<UserDetail>> {
        return allUserDetail
    }

    private class InsertLessonAsyncTask(private val userDetailDao: UserDetailDao) :
        AsyncTask<UserDetail, Void, Void>() {

        override fun doInBackground(vararg userDetail: UserDetail): Void? {
            userDetailDao.insert(userDetail[0])
            return null
        }
    }

    private class UpdateLessonAsyncTask(private val userDetailDao: UserDetailDao) :
        AsyncTask<UserDetail, Void, Void>() {
        override fun doInBackground(vararg userDetail: UserDetail): Void? {
            userDetailDao.update(userDetail[0])
            return null
        }
    }

    private class DeleteLessonAsyncTask (private val userDetailDao: UserDetailDao) :
        AsyncTask<UserDetail, Void, Void>() {

        override fun doInBackground(vararg userDetail: UserDetail): Void? {
            userDetailDao.delete(userDetail[0])
            return null
        }
    }

    private class DeleteLessonAsyncTaskById (private val userDetailDao: UserDetailDao) :
        AsyncTask<String, Void, String>() {

        override fun doInBackground(vararg id: String?): String? {
            return id[0]
        }

        override fun onPostExecute(result: String?) {
            DeleteLessonAsyncTaskById(
                userDetailDao
            ).execute(result!!)
//            lessonDao.delete(result!!)
        }
    }



}