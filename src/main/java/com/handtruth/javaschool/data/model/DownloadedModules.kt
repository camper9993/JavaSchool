package com.handtruth.javaschool.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "downloadedModules_table")
data class DownloadedModules(
    @PrimaryKey
    @SerializedName("id")
    @Expose
    val id: Int,

    @SerializedName("name")
    @Expose
    val name: String,

    @SerializedName("complexity")
    @Expose
    val complexity: Int,

    @SerializedName("lessonsCount")
    @Expose
    val lessonsCount: Int,

    @SerializedName("powerPoints")
    @Expose
    val powerPoints: Int,

    @SerializedName("description")
    @Expose
    val description: String = "",

    @SerializedName("imgId")
    @Expose
    val imgId: String = "",

    val imgIdRes: Int = 0,

    var isDownloaded: Boolean = false
) {
    constructor(moduleInfo: ModuleInfo) : this(
        moduleInfo.id,
        moduleInfo.name,
        moduleInfo.complexity,
        moduleInfo.lessonsCount,
        moduleInfo.powerPoints,
        moduleInfo.description,
        moduleInfo.imgId,
        moduleInfo.imgIdRes
    )
}