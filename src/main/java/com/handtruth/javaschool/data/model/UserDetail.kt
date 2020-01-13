package com.handtruth.javaschool.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity(tableName = "userDetail_table")
data class UserDetail (


    @SerializedName("firstName")
    @Expose
    val firstName: String = "",

    @SerializedName("secondName")
    @Expose
    val secondName: String = "",

    @SerializedName("age")
    @Expose
    val age: String = "",

    @PrimaryKey
    @SerializedName("login")
    @Expose
    val login: String,

    @SerializedName("password")
    @Expose
    val password: String
)