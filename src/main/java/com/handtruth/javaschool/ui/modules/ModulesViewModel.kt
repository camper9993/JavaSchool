package com.handtruth.javaschool.ui.modules

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.handtruth.javaschool.data.model.DownloadedModules
import com.handtruth.javaschool.data.model.ModuleInfo
import com.handtruth.javaschool.data.model.Subscriber
import com.handtruth.javaschool.data.repo.DownloadedModulesRepo
import com.handtruth.javaschool.data.repo.ModuleInfoRepo

class ModulesViewModel(var application: Application? = null) : ViewModel(), Subscriber<DownloadedModules> {
    private lateinit var repository: DownloadedModulesRepo
    private lateinit var allModuleInfo: LiveData<List<DownloadedModules>>

    fun setApplication() {
        repository = DownloadedModulesRepo(application)
        allModuleInfo = repository.getAllModuleInfo()
    }


    override fun insert(obj: DownloadedModules) {
        repository.insert(obj)
    }

    fun insert(moduleInfo: List<DownloadedModules>) {
        for (m in moduleInfo) {
            repository.insert(m)
        }
    }

    fun update(moduleInfo: DownloadedModules) {
        repository.update(moduleInfo)
    }

    fun delete(moduleInfo: DownloadedModules) {
        repository.delete(moduleInfo)
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