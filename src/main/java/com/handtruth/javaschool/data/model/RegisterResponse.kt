package com.handtruth.javaschool.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("code")
    @Expose
    val code: Int,

    @SerializedName("user")
    @Expose
    val user: UserDetail?
)