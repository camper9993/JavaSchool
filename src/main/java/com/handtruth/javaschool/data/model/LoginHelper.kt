package com.handtruth.javaschool.data.model

import android.app.Application
import android.util.Log
import android.widget.Toast
import com.handtruth.javaschool.connect.RepositoryProvider
import com.handtruth.javaschool.data.LoginRepository
import com.handtruth.javaschool.data.repo.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlin.math.log

object LoginHelper {
    private lateinit var modulesRepo: ModuleInfoRepo
    private lateinit var downloadedModulesRepo: DownloadedModulesRepo
    private lateinit var lessonsRepo: LessonsRepo
    private lateinit var moduleTestRepo: ModuleTestsRepo
    private lateinit var testRepo: TestRepo
    private lateinit var userDetailRepo: UserDetailRepo

    lateinit var application: Application

    fun setDB(application: Application) {
        modulesRepo = ModuleInfoRepo(application)
        downloadedModulesRepo = DownloadedModulesRepo(application)
        lessonsRepo = LessonsRepo(application)
        moduleTestRepo = ModuleTestsRepo(application)
        testRepo = TestRepo(application)
        userDetailRepo = UserDetailRepo(application)
    }


    var downloadedModules: List<Int> = emptyList()
    var downloadedTests: List<Int> = emptyList()


    fun downloadAll() {
        clearAll()
        RepositoryProvider.provideFilesRepository().getModules()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->
                for (r in result) {
                    if (downloadedModules.contains(r.id)) {
                        val dwnld = DownloadedModules(r)
                        dwnld.isDownloaded = downloadedTests.contains(r.id)
                        downloadedModulesRepo.insertOrUpdate(dwnld)
                    } else {
                        modulesRepo.insertOrUpdate(r)
                    }
                }
            }, { error ->
                error.printStackTrace()
            })
        for (id in downloadedModules) {
            RepositoryProvider.provideFilesRepository()
                .getModule(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ module ->
                    lessonsRepo.insertOrUpdate(module)
                }, { error ->
                    error.printStackTrace()
                })
        }
        for (id in downloadedTests) {
            RepositoryProvider.provideFilesRepository().getLessonTests(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ result ->
                    RepositoryProvider.provideFilesRepository().getTests(id)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({ tests ->
                            moduleTestRepo.insertAll(result)
                            testRepo.insertAll(tests)
                            downloadedTests = emptyList()
                        }, { error ->
                            error.printStackTrace()
                        })

                }, { error ->
                    error.printStackTrace()
                })
        }
//        downloadedModules = emptyList()
    }

    fun clearAll() {
        modulesRepo.deleteAllModuleInfo()
        downloadedModulesRepo.deleteAllModuleInfo()
        lessonsRepo.deleteAllModules()
        moduleTestRepo.deleteAllModuleTests()
        testRepo.deleteAllTests()
//        userDetailRepo.delete()
    }

    fun hasCurrent(): Boolean {
        val users = userDetailRepo.getAllUser().value
        println(users.toString())
        if (users.isNullOrEmpty()) {
            return false
        } else {
            LoginRepository.user = users[0]
            return true
        }
    }

    fun setCurrent(user: UserDetail, application: Application) {
        userDetailRepo = UserDetailRepo(application)
        userDetailRepo.insertOrUpdate(user)
    }
}
