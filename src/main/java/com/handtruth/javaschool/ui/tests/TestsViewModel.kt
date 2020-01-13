package com.handtruth.javaschool.ui.tests

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.handtruth.javaschool.data.model.DownloadedModules
import com.handtruth.javaschool.data.model.ModuleInfo
import com.handtruth.javaschool.data.model.Subscriber
import com.handtruth.javaschool.data.repo.DownloadedModulesRepo
import com.handtruth.javaschool.data.repo.ModuleInfoRepo

class TestsViewModel : ViewModel(), Subscriber<DownloadedModules> {
    private lateinit var  repository: DownloadedModulesRepo
    private lateinit var allModuleInfo: LiveData<List<DownloadedModules>>

    fun setApplication(application: Application) {
        repository = DownloadedModulesRepo(application)
        allModuleInfo = repository.getAllModuleInfo()
    }

    override fun insert(obj: DownloadedModules) {
        repository.insert(obj)
    }

    fun insert(moduleInfos: List<DownloadedModules>) {
        for (m in moduleInfos) {
            repository.insert(m)
        }
    }

    fun update(downloadedModules: DownloadedModules) {
        repository.update(downloadedModules)
    }

    fun delete(downloadedModules: DownloadedModules) {
        repository.delete(downloadedModules)
    }

    override fun delete(id: Int) {
        repository.delete(id)
    }

    fun deleteAllModuleInfo() {
        repository.deleteAllModuleInfo()
    }

    fun getAllModuleInfo(): LiveData<List<DownloadedModules>> {
        return allModuleInfo
    }

    fun updateAllModules() {
        allModuleInfo = repository.getAllModuleInfo()
    }
}