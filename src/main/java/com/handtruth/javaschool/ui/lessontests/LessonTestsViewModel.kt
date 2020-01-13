package com.handtruth.javaschool.ui.lessontests

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.handtruth.javaschool.data.model.ModuleTests
import com.handtruth.javaschool.data.repo.ModuleTestsRepo
import com.handtruth.javaschool.ui.TestActivity

class LessonTestsViewModel : ViewModel() {
    private lateinit var repository: ModuleTestsRepo
    private lateinit var allModuleTests: LiveData<List<ModuleTests>>

    fun setApplication(application: Application) {
        repository = ModuleTestsRepo(application)
        allModuleTests = repository.getModuleTestsById(TestActivity.id)
    }

    fun insert(test: ModuleTests) {
        repository.insert(test)
    }

    fun insert(tests: List<ModuleTests>) {
        for (m in tests) {
            repository.insert(m)
        }
    }

    fun update(test: ModuleTests) {
        repository.update(test)
    }

    fun delete(test: ModuleTests) {
        repository.delete(test)
    }

    fun delete(id: Int) {
        repository.delete(id)
    }

    fun deleteAllTest() {
        repository.deleteAllModuleTests()
    }

    fun getAllTest(): LiveData<List<ModuleTests>> {
        return allModuleTests
    }

    fun updateAllTest() {
        allModuleTests = repository.getModuleTestsById(TestActivity.id)
    }
}
