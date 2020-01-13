package com.handtruth.javaschool.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.handtruth.javaschool.data.model.ModuleInfo

@Dao
interface ModuleInfoDao {

    @Insert
    fun insert(moduleInfo: ModuleInfo)

    @Update
    fun update(moduleInfo: ModuleInfo)

    @Delete
    fun delete(moduleInfo: ModuleInfo)

    @Query("DELETE FROM moduleInfo_table WHERE moduleInfo_table.id = :id")
    fun delete(id: Int)

    @Query("DELETE FROM moduleInfo_table")
    fun deleteAll()

    @Query("SELECT * FROM moduleInfo_table")
    fun getAllModuleInfo(): LiveData<List<ModuleInfo>>

}