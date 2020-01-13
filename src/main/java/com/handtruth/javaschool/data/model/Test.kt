package com.handtruth.javaschool.data.model

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "test_table")
data class Test (

    @PrimaryKey
    @SerializedName("id")
    @Expose
    val id: Int,

    @SerializedName("id_module")
    @Expose
    val id_module: Int,

    @SerializedName("id_lesson")
    @Expose
    val id_lesson: Int,

    @SerializedName("img_q")
    @Expose
    val img_q: String = "",

    @SerializedName("question")
    @Expose
    val question: String,

    @SerializedName("answer1")
    @Expose
    val answer1: String,

    @SerializedName("answer2")
    @Expose
    val answer2: String,

    @SerializedName("answer3")
    @Expose
    val answer3: String,

    @SerializedName("answer4")
    @Expose
    val answer4: String,

    @SerializedName("true_answ")
    @Expose
    val trueAnsw: Int,

    @SerializedName("state")
    @Expose
    var state: Int
)