package com.handtruth.javaschool.data

import android.util.Patterns


// A placeholder username validation check
fun isUserNameValid(username: String): Boolean {
    return if (username.contains('@')) {
        Patterns.EMAIL_ADDRESS.matcher(username).matches()
    } else {
        false
    }
}

// A placeholder password validation check
fun isPasswordValid(password: String): Boolean {
    return password.length >= 8
}

// A placeholder password validation check
fun isPasswordEql(password: String, secPassword: String): Boolean {
    return password == secPassword
}

