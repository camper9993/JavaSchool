package com.handtruth.javaschool.ui.user

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.handtruth.javaschool.R
import com.handtruth.javaschool.data.LoginRepository
import com.handtruth.javaschool.ui.init.InitActivity
import kotlinx.android.synthetic.main.fragment_user.*
import kotlinx.android.synthetic.main.fragment_user.view.*
import java.io.IOException

class UserFragment : Fragment() {

    companion object {
        fun newInstance() = UserFragment()
        lateinit var imageView : ImageButton
    }

    private lateinit var viewModel: UserViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_user, container,false)
        root.imageButton2.setOnClickListener {
            chooseImage()
        }

        val button = root.findViewById<Button>(R.id.logout)
        button.setOnClickListener {
            LoginRepository.logout()
            val intent = Intent(this.context, InitActivity::class.java)
            this.activity!!.setResult(1)
            startActivity(intent)
            this.activity!!.finish()
        }
        root.user_email.setText(LoginRepository.user!!.login)
        root.user_name.text = "${LoginRepository.user!!.firstName} ${LoginRepository.user!!.secondName}"
        root.user_age.text = LoginRepository.user!!.age


        return root
    }

    private fun chooseImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent,"Select picture"), 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1  && resultCode == RESULT_OK && data != null && data.data != null) {
            val  uri = data.data

            try {
                val bitmap = MediaStore.Images.Media.getBitmap(activity!!.contentResolver, uri)

                imageView = activity!!.imageButton2
                imageView.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.e("KEK", "onActivityCreated called!")
        viewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        val sharedPreferences = this.activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)
    }



}