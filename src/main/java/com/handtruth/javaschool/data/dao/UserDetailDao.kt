package com.handtruth.javaschool.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.handtruth.javaschool.data.model.Test
import com.handtruth.javaschool.data.model.UserDetail

@Dao
interface UserDetailDao {
    @Insert
    fun insert(userDetail: UserDetail)

    @Update
    fun update(userDetail: UserDetail)

    @Delete
    fun delete(userDetail: UserDetail)

    @Query("DELETE  FROM userDetail_table")
    fun deleteAll()

    @Query("DELETE FROM userDetail_table WHERE userDetail_table.login = :login")
    fun delete(login: String)

    @Query("SELECT * FROM userDetail_table")
    fun getAllTests(): LiveData<List<UserDetail>>
}