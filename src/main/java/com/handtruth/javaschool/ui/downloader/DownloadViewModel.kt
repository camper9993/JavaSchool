package com.handtruth.javaschool.ui.downloader

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import com.handtruth.javaschool.data.model.ModuleInfo
import com.handtruth.javaschool.data.model.Subscriber
import com.handtruth.javaschool.data.repo.DownloadedModulesRepo
import com.handtruth.javaschool.data.repo.ModuleInfoRepo


class DownloadViewModel : ViewModel(), Subscriber<ModuleInfo> {

    private lateinit var repository: ModuleInfoRepo
    private lateinit var rep: DownloadedModulesRepo
    private lateinit var allModuleInfo: LiveData<List<ModuleInfo>>

    fun setApplication(application: Application) {
        repository = ModuleInfoRepo(application)
        rep = DownloadedModulesRepo(application)
        allModuleInfo = repository.getAllModuleInfo()
    }

    override fun insert(obj: ModuleInfo) {
        repository.insert(obj)
    }

    fun insert(moduleInfo: List<ModuleInfo>) {
        for (m in moduleInfo) {
            repository.insert(m)
        }
    }

    fun insertOrUpdate(moduleInfo: List<ModuleInfo>) {
        rep.getAllModuleInfo()
        repository.insertOrUpdate(moduleInfo)
    }

    fun update(moduleInfo: ModuleInfo) {
        repository.update(moduleInfo)
    }

    fun update(moduleInfo: List<ModuleInfo>) {
        for (m in moduleInfo) {
            repository.update(m)
        }
    }

    fun delete(moduleInfo: ModuleInfo) {
        repository.delete(moduleInfo)
    }

    override fun delete(id: Int) {
        repository.delete(id)
    }

    fun deleteAllModuleInfo() {
        repository.deleteAllModuleInfo()
    }

    fun getAllModuleInfo(): LiveData<List<ModuleInfo>> {
        return allModuleInfo
    }

}
