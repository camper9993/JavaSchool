package com.handtruth.javaschool.data.repo

import android.app.Application
import androidx.lifecycle.LiveData
import com.handtruth.javaschool.data.model.ModuleInfo
import android.os.AsyncTask
import com.handtruth.javaschool.data.dao.DownloadedModulesDao
import com.handtruth.javaschool.data.dao.ModuleInfoDao
import com.handtruth.javaschool.data.database.DownloadedModulesDatabase
import com.handtruth.javaschool.data.database.ModuleInfoDatabase
import com.handtruth.javaschool.data.model.DownloadedModules


class ModuleInfoRepo(val application : Application? = null) {

    private val allModuleInfo: LiveData<List<ModuleInfo>>
    private val helpList: LiveData<List<DownloadedModules>>
    private val moduleInfoDao: ModuleInfoDao
    private val downloadedModulesDao: DownloadedModulesDao


    init {
        val moduleInfoDatabase : ModuleInfoDatabase = application?.let {
            ModuleInfoDatabase.getInstance(
                it
            )
        }!!
        val downloadedModulesDatabase : DownloadedModulesDatabase = application.let {
            DownloadedModulesDatabase.getInstance(
                it
            )
        }!!
        moduleInfoDao = moduleInfoDatabase.moduleInfoDao()
        allModuleInfo = moduleInfoDao.getAllModuleInfo()
        downloadedModulesDao = downloadedModulesDatabase.moduleInfoDao()
        helpList = downloadedModulesDao.getAllModules()
    }

    fun insertOrUpdate(moduleInfo: ModuleInfo) {
        val list = allModuleInfo.value
        val list1 = helpList.value
        if (list.isNullOrEmpty())
            insert(moduleInfo)
        else {
            for (item in list1!!)
                    list.forEach {
                        if (it.id != item.id) {
                            if (it.id == moduleInfo.id) {
                                update(moduleInfo)
                                return
                            }
                            insert(moduleInfo)
                        }
                    }
        }
    }

    fun insertOrUpdate(moduleInfos: List<ModuleInfo>) {
        moduleInfos.forEach {
            insertOrUpdate(it)
        }
    }

    fun insert(moduleInfo: ModuleInfo) {
        InsertModuleInfoAsyncTask(moduleInfoDao).execute(moduleInfo)
    }

    fun update(moduleInfo: ModuleInfo) {
        UpdateModuleInfoAsyncTask(moduleInfoDao).execute(moduleInfo)
    }

    fun delete(moduleInfo: ModuleInfo) {
        DeleteModuleInfoAsyncTask(moduleInfoDao).execute(moduleInfo)
    }

    fun delete(id: Int) {
        DeleteModuleInfoAsyncTaskById(
            moduleInfoDao
        ).execute(id)
    }

    fun deleteAllModuleInfo() {
        DeleteAllModuleInfoAsyncTask(moduleInfoDao)
            .execute()
    }

    fun getAllModuleInfo(): LiveData<List<ModuleInfo>> {
        return allModuleInfo
    }

    private class InsertModuleInfoAsyncTask(private val moduleInfoDao: ModuleInfoDao) :
        AsyncTask<ModuleInfo, Void, Void>() {

        override fun doInBackground(vararg moduleInfo: ModuleInfo): Void? {
            moduleInfoDao.insert(moduleInfo[0])
            return null
        }
    }

    private class UpdateModuleInfoAsyncTask(private val moduleInfoDao: ModuleInfoDao) :
        AsyncTask<ModuleInfo, Void, Void>() {

        override fun doInBackground(vararg moduleInfo: ModuleInfo): Void? {
            moduleInfoDao.update(moduleInfo[0])
            return null
        }
    }

    private class DeleteModuleInfoAsyncTask (private val moduleInfoDao: ModuleInfoDao) :
        AsyncTask<ModuleInfo, Void, Void>() {

        override fun doInBackground(vararg moduleInfo: ModuleInfo): Void? {
            moduleInfoDao.delete(moduleInfo[0])
            return null
        }
    }

    private class DeleteModuleInfoAsyncTaskById (private val moduleInfoDao: ModuleInfoDao) :
        AsyncTask<Int, Void, Int>() {

        override fun doInBackground(vararg id: Int?): Int? {
            return id[0]
        }

        override fun onPostExecute(result: Int?) {
            DeleteModuleInfoAsyncTaskById(
                moduleInfoDao
            ).execute(result!!)
//            moduleInfoDao.delete(result!!)
        }
    }

    private class DeleteAllModuleInfoAsyncTask (private val moduleInfoDao: ModuleInfoDao) :
        AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg voids: Void): Void? {
            moduleInfoDao.deleteAll()
            return null
        }
    }

}