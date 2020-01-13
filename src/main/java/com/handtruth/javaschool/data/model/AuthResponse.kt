package com.handtruth.javaschool.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("code")
    @Expose
    val code: Int,

    @SerializedName("token")
    @Expose
    val  token: String,

    @SerializedName("user")
    @Expose
    val user: AllUserData?
)