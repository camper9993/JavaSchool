package com.handtruth.javaschool.ui.init

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.handtruth.javaschool.R
import com.handtruth.javaschool.data.LoginRepository
import com.handtruth.javaschool.data.model.LoginHelper
import com.handtruth.javaschool.data.model.UserShared
import com.handtruth.javaschool.ui.MainActivity
import com.handtruth.javaschool.ui.login.LoginActivity


class InitActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private val APP_PREFERENCES = "USER"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_init)
        sharedPreferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        LoginHelper.setDB(application)

        UserShared.setApplication(sharedPreferences)
        val b = UserShared.isExisting()
        println(b.toString())
        if (b) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            val intent = Intent(this, LoginActivity::class.java)
            startActivityForResult(intent, 200)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            200 -> {
                if (resultCode == 1) {
                    val intent = Intent(this, MainActivity::class.java)
                    LoginHelper.downloadAll()
                    startActivity(intent)
                    finish()
                } else {
                    finish()
                }
            }
        }
    }
}
