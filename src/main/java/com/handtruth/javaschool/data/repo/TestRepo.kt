package com.handtruth.javaschool.data.repo

import android.app.Application
import androidx.lifecycle.LiveData
import android.os.AsyncTask
import com.handtruth.javaschool.data.dao.TestDao
import com.handtruth.javaschool.data.database.TestDatabase
import com.handtruth.javaschool.data.model.Test


class TestRepo (val application : Application? = null) {

    private val allTest: LiveData<List<Test>>
    private val testDao: TestDao


    init {
        val testDatabase : TestDatabase = application?.let {
            TestDatabase.getInstance(
                it
            )
        }!!
        testDao = testDatabase.testDao()
        allTest = testDao.getAllTests()
    }

    fun insertAll(tests: List<Test>) {
        tests.forEach {
            insert(it)
        }
    }

    private fun insertOrUpdate(test: Test) {
        val list = allTest.value
        if (list.isNullOrEmpty())
            insert(test)
        else {
            list.forEach {
                if (it.id == test.id) {
                    if (it.copy(state = -1) != test) {
                        update(test)
                        return
                    }
                } else
                    if (it.id != test.id)
                        insert(test)
            }
        }
    }

    fun insertOrUpdate(moduleInfos: List<Test>) {
        moduleInfos.forEach {
            insertOrUpdate(it)
        }
    }

    fun insert(test: Test) {
        InsertTestAsyncTask(testDao).execute(test)
    }

    fun update(test: Test) {
        UpdateTestAsyncTask(testDao).execute(test)
    }

    fun delete(test: Test) {
        DeleteTestAsyncTask(testDao).execute(test)
    }

    fun delete(id: Int) {
        DeleteTestAsyncTaskById(
            testDao
        ).execute(id)
    }

    fun deleteAllTests() {
        DeleteAllTestAsyncTask(testDao)
            .execute()
    }

    fun getAllTests(): LiveData<List<Test>> {
        return allTest
    }

    fun getTestsByIds(moduleID: Int, lessonID: Int): LiveData<List<Test>> {
        return testDao.getTestsByIds(moduleID,lessonID)
    }



    private class InsertTestAsyncTask(private val testDao: TestDao) :
        AsyncTask<Test, Void, Void>() {

        override fun doInBackground(vararg test: Test): Void? {
            testDao.insert(test[0])
            return null
        }
    }

    private class UpdateTestAsyncTask(private val testDao: TestDao) :
        AsyncTask<Test, Void, Void>() {
        override fun doInBackground(vararg test: Test): Void? {
            testDao.update(test[0])
            return null
        }
    }

    private class DeleteTestAsyncTask (private val testDao: TestDao) :
        AsyncTask<Test, Void, Void>() {

        override fun doInBackground(vararg test: Test): Void? {
            testDao.delete(test[0])
            return null
        }
    }

    private class DeleteTestAsyncTaskById (private val testDao: TestDao) :
        AsyncTask<Int, Void, Int>() {

        override fun doInBackground(vararg id: Int?): Int? {
            return id[0]
        }

        override fun onPostExecute(result: Int?) {
            DeleteTestAsyncTaskById(
                testDao
            ).execute(result!!)
//            TestDao.delete(result!!)
        }
    }

    private class DeleteAllTestAsyncTask (private val testDao: TestDao) :
        AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg voids: Void): Void? {
            testDao.deleteAllTests()
            return null
        }
    }

}