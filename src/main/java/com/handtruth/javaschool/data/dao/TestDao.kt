package com.handtruth.javaschool.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.handtruth.javaschool.data.model.ModuleTests
import com.handtruth.javaschool.data.model.Test

@Dao
interface TestDao {
    @Insert
    fun insert(test: Test)

    @Update
    fun update(test: Test)

    @Delete
    fun delete(test: Test)

    @Query("DELETE FROM test_table WHERE test_table.id = :id")
    fun delete(id: Int)

    @Query("DELETE  FROM test_table")
    fun deleteAllTests()

    @Query("SELECT * FROM test_table WHERE test_table.id_lesson = :lessonID AND test_table.id_module = :moduleID ")
    fun getTestsByIds(moduleID: Int, lessonID: Int): LiveData<List<Test>>

    @Query("SELECT * FROM test_table")
    fun getAllTests(): LiveData<List<Test>>
}