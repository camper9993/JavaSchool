package com.handtruth.javaschool.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.handtruth.javaschool.R

class TestActivity : AppCompatActivity() {

    companion object {
        var id: Int = 0
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        id = intent.getIntExtra("MODULE_ID", 0)
        Log.d("TEST_ACTIVITY", id.toString())
    }
}
