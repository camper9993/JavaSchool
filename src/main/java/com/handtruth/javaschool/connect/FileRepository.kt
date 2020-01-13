package com.handtruth.javaschool.connect

import com.handtruth.javaschool.data.model.*
import io.reactivex.Observable
import java.io.InputStream

class FileRepository(private val apiService: ApiService) {

    fun getModules(): Observable<List<ModuleInfo>> {
        return apiService.getModules()
    }

    fun getModule(id: Int): Observable<List<Lesson>> {
        return apiService.getModule(id)
    }

    fun getModuleInfo(id: Int): Observable<ModuleInfo> {
        return apiService.getModuleInfo(id)
    }

    fun getTests(id: Int): Observable<List<Test>> {
        return apiService.getTests(id)
    }

    fun getLessonTests(id: Int): Observable<List<ModuleTests>> {
        return apiService.getLessonTests(id)
    }

    fun download(id: String): Observable<String> {
        return apiService.download(id)
    }

}