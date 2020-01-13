package com.handtruth.javaschool.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.handtruth.javaschool.R

class ModuleActivity : AppCompatActivity() {

    companion object {
        var id: Int = 0
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_module)

        id = intent.getIntExtra("MODULE_ID", 0)
    }
}
