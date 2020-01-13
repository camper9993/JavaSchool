package com.handtruth.javaschool.data.model

import android.annotation.SuppressLint
import android.app.Application
import android.content.SharedPreferences
import com.handtruth.javaschool.data.LoginRepository

object UserShared {
    private lateinit var sharedPreferences: SharedPreferences

    fun setApplication (sharedPreferences: SharedPreferences) {
        this.sharedPreferences = sharedPreferences
    }

    @SuppressLint("CommitPrefEdits")
    fun save(userDetail: UserDetail) {
        println(userDetail.toString())
        val editor = sharedPreferences.edit()
        editor.putString("login", userDetail.login)
        editor.putString("password", userDetail.password)
        editor.putString("firstName", userDetail.firstName)
        editor.putString("secondName", userDetail.secondName)
        editor.putString("age", userDetail.age)
        editor.apply()
    }

    fun isExisting():Boolean {
        if (sharedPreferences.getString("login", "") == "") {
            return false
        } else {
            val login = sharedPreferences.getString("login", "")
            val password = sharedPreferences.getString("password", "")
            val firstName = sharedPreferences.getString("firstName", "")
            val secondName = sharedPreferences.getString("secondName", "")
            val age  = sharedPreferences.getString("age", "")
            LoginRepository.user = UserDetail(firstName, secondName, age, login, password)
            return true
        }
    }

    fun clear() {
        val editor = sharedPreferences.edit()
        editor.putString("login", "")
        editor.apply()
    }

}