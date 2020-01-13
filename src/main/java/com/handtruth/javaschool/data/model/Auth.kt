package com.handtruth.javaschool.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



data class Auth (
    @SerializedName("login")
    @Expose
    val login: String,

    @SerializedName("password")
    @Expose
    val password: String
)