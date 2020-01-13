package com.handtruth.javaschool.ui.lesson

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.handtruth.javaschool.data.model.Lesson
import com.handtruth.javaschool.data.repo.LessonsRepo
import com.handtruth.javaschool.ui.ModuleActivity

class LessonViewModel : ViewModel() {
    private lateinit var repository: LessonsRepo
    private lateinit var allModuleTests: LiveData<List<Lesson>>

    fun setApplication(application: Application) {
        repository = LessonsRepo(application)
        allModuleTests = repository.getLessonsByid(ModuleActivity.id)
        println(allModuleTests.value?.get(0)?.content)
    }

    fun insert(lesson: Lesson) {
        repository.insert(lesson)
    }

    fun insert(tests: List<Lesson>) {
        for (m in tests) {
            repository.insert(m)
        }
    }

    fun update(test: Lesson) {
        repository.update(test)
    }

    fun delete(test: Lesson) {
        repository.delete(test)
    }

    fun delete(id: Int) {
        repository.delete(id)
    }

    fun getContent(idLesson: Int) : LiveData<Lesson> {
        return repository.getContent(idLesson)
    }


    fun getAllTest(): LiveData<List<Lesson>> {
        return allModuleTests
    }

    fun updateAllTest() {
        allModuleTests = repository.getLessonsByid(ModuleActivity.id)
    }
}