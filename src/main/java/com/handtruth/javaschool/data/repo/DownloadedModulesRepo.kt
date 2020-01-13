package com.handtruth.javaschool.data.repo

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.handtruth.javaschool.data.dao.DownloadedModulesDao
import com.handtruth.javaschool.data.database.DownloadedModulesDatabase
import com.handtruth.javaschool.data.model.DownloadedModules
import com.handtruth.javaschool.data.model.Lesson
import com.handtruth.javaschool.data.model.ModuleInfo
import com.handtruth.javaschool.data.model.Test

class DownloadedModulesRepo(val application : Application? = null) {
    private val allModuleInfo: LiveData<List<DownloadedModules>>
    private val downloadedModulesDao: DownloadedModulesDao


    init {
        val downloadedModulesDatabase : DownloadedModulesDatabase = application?.let {
            DownloadedModulesDatabase.getInstance(
                it
            )
        }!!
        downloadedModulesDao = downloadedModulesDatabase.moduleInfoDao()
        allModuleInfo = downloadedModulesDao.getAllModules()
    }

    fun insertOrUpdate(downloadedModules: DownloadedModules) {
        val list = allModuleInfo.value
        if (list.isNullOrEmpty())
            insert(downloadedModules)
        else {
            list.forEach {
                if (it.id == downloadedModules.id) {
                    update(downloadedModules)
                    return
                }
                else insert(downloadedModules)
            }
        }
    }

    fun insertOrUpdate(moduleInfos: List<DownloadedModules>) {
        moduleInfos.forEach {
            insertOrUpdate(it)
        }
    }

    fun insert(downloadedModules: DownloadedModules) {
        InsertDownloadedModulesAsyncTask(downloadedModulesDao).execute(downloadedModules)
    }

    fun update(downloadedModules: DownloadedModules) {
        UpdateModuleInfoAsyncTask(downloadedModulesDao).execute(downloadedModules)
    }

    fun delete(downloadedModules: DownloadedModules) {
        DeleteModuleInfoAsyncTask(downloadedModulesDao).execute(downloadedModules)
    }

    fun delete(id: Int) {
        DeleteModuleInfoAsyncTaskById(downloadedModulesDao).execute(id)
    }

    fun deleteAllModuleInfo() {
        DeleteDownloadedModulesAsyncTask(downloadedModulesDao).execute()
    }

    fun getAllModuleInfo(): LiveData<List<DownloadedModules>> {
        return allModuleInfo
    }

    private class InsertDownloadedModulesAsyncTask(private val moduleInfoDaoDownloaded: DownloadedModulesDao) :
        AsyncTask<DownloadedModules, Void, Void>() {

        override fun doInBackground(vararg downloadedModules: DownloadedModules): Void? {
            moduleInfoDaoDownloaded.insert(downloadedModules[0])
            return null
        }
    }

    private class UpdateModuleInfoAsyncTask(private val moduleInfoDaoDownloaded: DownloadedModulesDao) :
        AsyncTask<DownloadedModules, Void, Void>() {

        override fun doInBackground(vararg downloadedModules: DownloadedModules): Void? {
            moduleInfoDaoDownloaded.update(downloadedModules[0])
            return null
        }
    }

    private class DeleteModuleInfoAsyncTask (private val moduleInfoDaoDownloaded: DownloadedModulesDao) :
        AsyncTask<DownloadedModules, Void, Void>() {

        override fun doInBackground(vararg downloadedModules: DownloadedModules): Void? {
            moduleInfoDaoDownloaded.delete(downloadedModules[0])
            return null
        }
    }

    private class DeleteModuleInfoAsyncTaskById (private val moduleInfoDaoDownloaded: DownloadedModulesDao) :
        AsyncTask<Int, Void, Int>() {

        override fun doInBackground(vararg id: Int?): Int? {
            return id[0]
        }

        override fun onPostExecute(result: Int?) {
            moduleInfoDaoDownloaded.delete(result!!)
        }
    }

    private class DeleteDownloadedModulesAsyncTask (private val moduleInfoDaoDownloaded: DownloadedModulesDao) :
        AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg voids: Void): Void? {
            moduleInfoDaoDownloaded.deleteAllModules()
            return null
        }
    }
}