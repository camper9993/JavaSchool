package com.handtruth.javaschool.connect

import android.util.Log
import com.handtruth.javaschool.data.model.AuthResponse
import com.handtruth.javaschool.data.model.RegisterResponse
import com.handtruth.javaschool.data.model.UserDetail
import io.reactivex.Observable
import io.reactivex.Single

class AuthRepository(private val apiService: ApiService) {

    fun auth(login: String, password: String): Single<AuthResponse> {
        val user = UserDetail(firstName = "Lexa", secondName = "Lexa", age = "100", login = login, password = password)
        Log.d("WARNING", user.toString())
        return apiService.auth(firstName = "Lexa", secondName = "Lexa", age = "100", login = login, password = password)
    }

    fun registration(user: UserDetail): Single<RegisterResponse> {
        return apiService.registration(
            firstName = user.firstName,
            secondName = user.secondName,
            age = user.age,
            login = user.login,
            password = user.password)
    }
}