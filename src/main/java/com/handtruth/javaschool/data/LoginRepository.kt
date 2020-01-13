package com.handtruth.javaschool.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.handtruth.javaschool.R
import com.handtruth.javaschool.connect.RepositoryProvider
import com.handtruth.javaschool.data.model.LoginHelper
import com.handtruth.javaschool.data.model.UserDetail
import com.handtruth.javaschool.data.model.UserShared
import com.handtruth.javaschool.data.repo.UserDetailRepo
import com.handtruth.javaschool.ui.login.LoggedInUserView
import com.handtruth.javaschool.ui.login.LoginResult
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.IOException

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

object LoginRepository {
    var user: UserDetail? = null
        internal set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        user = null
    }

    fun logout() {
        user = null
        UserShared.clear()
    }

    fun login(
        username: String,
        password: String,
        _loginResult: MutableLiveData<LoginResult>
    ) {
        RepositoryProvider.provideAuthRepository().auth(username, password)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                Log.d("WARNING", it.toString())
                val result = if (it.code == -1) {
                    Result.Error(IOException("Bad login"))
                } else {
                    if (it.user != null) {
                        Result.Success(it.user)
                    } else {
                        Result.Error(IOException("Empty User"))
                    }
                }

                if (result is Result.Success) {
                    _loginResult.value =
                        LoginResult(success = LoggedInUserView(displayName = "${result.data.userDetail.firstName} ${result.data.userDetail.secondName}"))
                    setLoggedInUser(result.data.userDetail)
                    LoginHelper.downloadedModules = result.data.downloadedModules
                    LoginHelper.downloadedTests = result.data.downloadedTests
                } else {
                    _loginResult.value = LoginResult(error = R.string.login_failed)
                    setLoggedInUser(null)
                }
            }, {
                Log.d("WARNING", it.toString())
            })
    }


    private fun setLoggedInUser(loggedInUser: UserDetail?) {
        this.user = loggedInUser
        UserShared.save(loggedInUser!!)
    }
}
