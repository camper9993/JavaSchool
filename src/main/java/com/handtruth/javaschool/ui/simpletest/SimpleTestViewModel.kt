package com.handtruth.javaschool.ui.simpletest

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.handtruth.javaschool.data.model.Test
import com.handtruth.javaschool.data.repo.TestRepo
import com.handtruth.javaschool.ui.TestActivity

class SimpleTestViewModel : ViewModel() {
    private lateinit var repository: TestRepo
    private lateinit var allModuleInfo: LiveData<List<Test>>


    fun setApplication(application: Application, id: Int) {
        repository = TestRepo(application)
        println(repository.getAllTests().value.toString())
        println(TestActivity.id)
        println(id)
        allModuleInfo = repository.getTestsByIds(TestActivity.id, id)
    }

    fun insert(test: Test) {
        repository.insert(test)
    }

    fun insert(tests: List<Test>) {
        for (m in tests) {
            repository.insert(m)
        }
    }

    fun update(test: Test) {
        repository.update(test)
    }

    fun delete(test: Test) {
        repository.delete(test)
    }

    fun delete(id: Int) {
        repository.delete(id)
    }

    fun deleteAllTest() {
        repository.deleteAllTests()
    }

    fun getAllTest(): LiveData<List<Test>> {
        return allModuleInfo
    }
}
