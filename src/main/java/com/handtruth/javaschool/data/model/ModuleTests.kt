package com.handtruth.javaschool.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "moduleTests_table")
data class ModuleTests(
    @PrimaryKey
    @SerializedName("id")
    @Expose
    val id: Int,

    @SerializedName("id_module")
    @Expose
    val id_module: Int,

    @SerializedName("name")
    @Expose
    val name: String,

    @SerializedName("test_count")
    @Expose
    val count: Int
//
//    @SerializedName("test_list")
//    @Expose
//    val testList: List<Test>
)