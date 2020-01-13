package com.handtruth.javaschool.data.model

import android.app.Application
import com.handtruth.javaschool.data.repo.*
import com.handtruth.javaschool.ui.modules.ModulesViewModel
import com.handtruth.javaschool.ui.tests.TestsViewModel

object ModelViewReceiver {

    fun setApplication(application: Application) {
        modulesRepo = DownloadedModulesRepo(application)
        lessonsRepo = LessonsRepo(application)
        moduleTestRepo = ModuleTestsRepo(application)
        testRepo = TestRepo(application)

    }

    var modulesViewModel: ModulesViewModel? = null
    var testsViewModel: TestsViewModel? = null

    private lateinit var modulesRepo: DownloadedModulesRepo
    private lateinit var moduleInfoRepo: ModuleInfoRepo
    private lateinit var lessonsRepo: LessonsRepo
    private lateinit var moduleTestRepo: ModuleTestsRepo
    private lateinit var testRepo: TestRepo

    fun publishModuleInfo(moduleInfo: ModuleInfo) {
        modulesRepo.insertOrUpdate(DownloadedModules(moduleInfo))
        testsViewModel?.updateAllModules()
    }

    fun publishModule(lessons: List<Lesson>) {
        lessonsRepo.insert(lessons)
    }

    fun publishModuleTests(tests: ModuleTests) {
        moduleTestRepo.insert(tests)
    }

    fun publishTests(tests: Test) {
        testRepo.insert(tests)
    }
}