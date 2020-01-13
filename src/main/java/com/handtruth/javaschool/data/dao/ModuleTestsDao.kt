package com.handtruth.javaschool.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.handtruth.javaschool.data.model.ModuleTests

@Dao
interface ModuleTestsDao {

    @Insert
    fun insert(moduleTests: ModuleTests)

    @Update
    fun update(moduleTests: ModuleTests)

    @Delete
    fun delete(moduleTests: ModuleTests)

    @Query("DELETE FROM moduleTests_table WHERE moduleTests_table.id = :id")
    fun delete(id: Int)

    @Query("DELETE FROM moduleTests_table")
    fun deleteAllTests()

//    fun getModuleInfoById(id: Int):

    @Query("SELECT * FROM moduleTests_table WHERE moduleTests_table.id_module = :id")
    fun getModuleTestsById(id: Int): LiveData<List<ModuleTests>>

    @Query("SELECT * FROM moduleTests_table")
    fun getAllTests(): LiveData<List<ModuleTests>>
}