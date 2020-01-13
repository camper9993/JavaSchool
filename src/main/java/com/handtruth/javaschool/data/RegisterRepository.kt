package com.handtruth.javaschool.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.handtruth.javaschool.R
import com.handtruth.javaschool.connect.RepositoryProvider
import com.handtruth.javaschool.data.model.UserDetail
import com.handtruth.javaschool.ui.login.LoggedInUserView
import com.handtruth.javaschool.ui.login.LoginResult
import com.handtruth.javaschool.ui.register.RegisterResult
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.IOException

class RegisterRepository (){

    fun registration(
        firstName: String,
        secondName: String,
        age: String,
        login: String,
        password: String,
        _registerResult: MutableLiveData<RegisterResult>
    ) {
        RepositoryProvider.provideAuthRepository().registration(UserDetail(firstName, secondName, age, login, password))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                Log.d("WARNING", it.toString())
                val result = if (it.code == -1) {
                    Result.Error(IOException("Bad registration"))
                } else {
                    if (it.user != null) {
                        Result.Success(it.user)
                    } else {
                        Result.Error(IOException("Empty User"))
                    }
                }

                if (result is Result.Success) {
                    _registerResult.value = RegisterResult(success = true)
                } else {
                    _registerResult.value = RegisterResult(error = R.string.login_failed)

                }
            }, {
                Log.d("WARNING", it.toString())
            })
    }
}
