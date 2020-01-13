package com.handtruth.javaschool.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.handtruth.javaschool.data.model.DownloadedModules
import com.handtruth.javaschool.data.model.ModuleInfo

@Dao
interface DownloadedModulesDao {
    @Insert
    fun insert(downloadedModules: DownloadedModules)

    @Update
    fun update(downloadedModules: DownloadedModules)

    @Delete
    fun delete(downloadedModules: DownloadedModules)

    @Query("DELETE  FROM downloadedModules_table")
    fun deleteAllModules()

    @Query("SELECT * FROM downloadedModules_table")
    fun getAllModules(): LiveData<List<DownloadedModules>>

    @Query("DELETE FROM downloadedModules_table WHERE downloadedModules_table.id = :id")
    fun delete(id: Int)
}