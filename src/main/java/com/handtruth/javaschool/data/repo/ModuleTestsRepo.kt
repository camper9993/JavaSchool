package com.handtruth.javaschool.data.repo

import android.app.Application
import androidx.lifecycle.LiveData
import android.os.AsyncTask
import com.handtruth.javaschool.data.dao.ModuleTestsDao
import com.handtruth.javaschool.data.database.ModuleTestDatabase
import com.handtruth.javaschool.data.model.ModuleTests


class ModuleTestsRepo (val application : Application? = null) {

    private val allModuleTests: LiveData<List<ModuleTests>>
    private val moduleTestsDao: ModuleTestsDao


    init {
        val moduleTestsDatabase : ModuleTestDatabase = application?.let {
            ModuleTestDatabase.getInstance(
                it
            )
        }!!
        moduleTestsDao = moduleTestsDatabase.moduleTestDao()
        allModuleTests = moduleTestsDao.getAllTests()
    }

    fun insertAll(moduleTests: List<ModuleTests>) {
        moduleTests.forEach {
            insert(it)
        }
    }

    private fun insertOrUpdate(moduleTests: ModuleTests) {
        val list = allModuleTests.value
        if (list.isNullOrEmpty())
            insert(moduleTests)
        else {
            list.forEach {
                if (it.id == moduleTests.id) {
                    update(moduleTests)
                    return
                } else insert(moduleTests)
            }
        }
    }


    fun insertOrUpdate(moduleInfos: List<ModuleTests>) {
        moduleInfos.forEach {
            insertOrUpdate(it)
        }
    }

    fun insert(moduleTests: ModuleTests) {
        InsertModuleTestsAsyncTask(moduleTestsDao).execute(moduleTests)
    }

    fun update(moduleTests: ModuleTests) {
        UpdateModuleTestsAsyncTask(moduleTestsDao).execute(moduleTests)
    }

    fun delete(moduleTests: ModuleTests) {
        DeleteModuleTestsAsyncTask(moduleTestsDao).execute(moduleTests)
    }

    fun delete(id: Int) {
        DeleteModuleTestsAsyncTaskById(
            moduleTestsDao
        ).execute(id)
//        ModuleTestsDao.delete(id)
    }

    fun deleteAllModuleTests() {
        DeleteAllModuleTestsAsyncTask(moduleTestsDao)
            .execute()
    }

    fun getAllModuleTests(): LiveData<List<ModuleTests>> {
        return allModuleTests
    }

    fun getModuleTestsById(moduleID: Int): LiveData<List<ModuleTests>> {
        return moduleTestsDao.getModuleTestsById(moduleID)
    }



    private class InsertModuleTestsAsyncTask(private val moduleTestsDao: ModuleTestsDao) :
        AsyncTask<ModuleTests, Void, Void>() {

        override fun doInBackground(vararg ModuleTests: ModuleTests): Void? {
            moduleTestsDao.insert(ModuleTests[0])
            return null
        }
    }

    private class UpdateModuleTestsAsyncTask(private val moduleTestsDao: ModuleTestsDao) :
        AsyncTask<ModuleTests, Void, Void>() {
        override fun doInBackground(vararg moduleTests: ModuleTests): Void? {
            moduleTestsDao.update(moduleTests[0])
            return null
        }
    }

    private class DeleteModuleTestsAsyncTask (private val moduleTestsDao: ModuleTestsDao) :
        AsyncTask<ModuleTests, Void, Void>() {

        override fun doInBackground(vararg moduleTests: ModuleTests): Void? {
            moduleTestsDao.delete(moduleTests[0])
            return null
        }
    }

    private class DeleteModuleTestsAsyncTaskById (private val moduleTestsDao: ModuleTestsDao) :
        AsyncTask<Int, Void, Int>() {

        override fun doInBackground(vararg id: Int?): Int? {
            return id[0]
        }

        override fun onPostExecute(result: Int?) {
            DeleteModuleTestsAsyncTaskById(
                moduleTestsDao
            ).execute(result!!)
//            ModuleTestsDao.delete(result!!)
        }
    }

    private class DeleteAllModuleTestsAsyncTask (private val moduleTestsDao: ModuleTestsDao) :
        AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg voids: Void): Void? {
            moduleTestsDao.deleteAllTests()
            return null
        }
    }

}