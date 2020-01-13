package com.handtruth.javaschool.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AllUserData(
    @SerializedName("user_detail")
    @Expose
    val userDetail: UserDetail,

    @SerializedName("downloaded_modules")
    @Expose
    val downloadedModules: List<Int>,

    @SerializedName("downloaded_tests")
    @Expose
    val downloadedTests: List<Int>
)