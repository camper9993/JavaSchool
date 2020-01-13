package com.handtruth.javaschool.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "lesson_table")
data class Lesson (
    @PrimaryKey
    @SerializedName("id")
    @Expose
    val id: Int,

    @SerializedName("module_id")
    @Expose
    val moduleId: Int,

    @SerializedName("name")
    @Expose
    val name: String,

    @SerializedName("lessonPoints")
    @Expose
    val lessonPoints: Int,

    @SerializedName("skills")
    @Expose
    val skills: String,

    @SerializedName("content")
    @Expose
    val content: String
)