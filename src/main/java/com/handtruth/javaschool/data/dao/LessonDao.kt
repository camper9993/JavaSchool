package com.handtruth.javaschool.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.handtruth.javaschool.data.model.Lesson
import com.handtruth.javaschool.data.model.ModuleInfo

@Dao
interface LessonDao {

    @Insert
    fun insert(lesson: Lesson)

    @Update
    fun update(lesson: Lesson)

    @Delete
    fun delete(lesson: Lesson)

    @Query("DELETE FROM lesson_table WHERE lesson_table.id = :id")
    fun delete(id: Int)

    @Query("DELETE FROM lesson_table")
    fun deleteAll()

    @Query("SELECT * FROM lesson_table where lesson_table.id = :idLesson")
    fun getContent(idLesson: Int): LiveData<Lesson>

    @Query("SELECT * FROM lesson_table where lesson_table.moduleId = :id")
    fun getLessonsByID(id: Int): LiveData<List<Lesson>>

    @Query("SELECT * FROM lesson_table")
    fun getAllModuleInfo(): LiveData<List<Lesson>>

}