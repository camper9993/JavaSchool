package com.handtruth.javaschool.connect

object RepositoryProvider {
    fun provideAuthRepository(): AuthRepository {
        return AuthRepository(ApiService.create())
    }

    fun provideFilesRepository(): FileRepository {
        return FileRepository(ApiService.create())
    }
}