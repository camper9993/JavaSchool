package com.handtruth.javaschool.ui.register

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.handtruth.javaschool.R
import com.handtruth.javaschool.ui.login.afterTextChanged



class RegisterActivity : AppCompatActivity() {
    private lateinit var registerViewModel: RegisterViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_register)

        val login = findViewById<EditText>(R.id.login)
        val firstName = findViewById<EditText>(R.id.fisrt_name)
        val secondName = findViewById<EditText>(R.id.second_name)
        val age = findViewById<EditText>(R.id.age)
        val password = findViewById<EditText>(R.id.password)
        val secPassword = findViewById<EditText>(R.id.sec_password)
        val reg = findViewById<Button>(R.id.button)
        val loading2 = findViewById<ProgressBar>(R.id.loading2)

        registerViewModel = ViewModelProviders.of(this, RegisterViewModelFactory())
            .get(RegisterViewModel::class.java)

        registerViewModel.registerFormState.observe( this@RegisterActivity, Observer {
            val registerState = it ?: return@Observer

            reg.isEnabled = registerState.isDataValid

            if (registerState.usernameError != null) {
                login.error = getString(registerState.usernameError)
            }
            if (registerState.passwordError != null) {
                password.error = getString(registerState.passwordError)
            }

            if (registerState.passwordSecError != null) {
                secPassword.error = getString(registerState.passwordSecError)
            }

            if (registerState.ageError != null) {
                age.error = getString(registerState.ageError)
            }
        })

        registerViewModel.registerResult.observe(this@RegisterActivity, Observer {
            val registerResult = it ?: return@Observer

            if (registerResult.error != null) {
                showRegisterFailed(registerResult.error)
            }
            if (registerResult.success) {
                updateUiWithUser()
            }

            val intent = Intent()
            intent.putExtra("login", login.text.toString())
            intent.putExtra("password", password.text.toString())
            setResult(Activity.RESULT_OK, intent)
            finish()
        })

        login.afterTextChanged {
            registerViewModel.registerDataChanged(
                login = login.text.toString(),
                firstName = firstName.text.toString(),
                secondName = secondName.text.toString(),
                age = age.text.toString(),
                password = password.text.toString(),
                secPassword = secPassword.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                registerViewModel.registerDataChanged(
                    login = login.text.toString(),
                    firstName = firstName.text.toString(),
                    secondName = secondName.text.toString(),
                    age = age.text.toString(),
                    password = password.text.toString(),
                    secPassword = secPassword.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                if (reg.isEnabled) {
                    when (actionId) {
                        EditorInfo.IME_ACTION_DONE ->
                            registerViewModel.registration(
                                login = login.text.toString(),
                                firstName = firstName.text.toString(),
                                secondName = secondName.text.toString(),
                                age = age.text.toString(),
                                password = password.text.toString()
                            )
                    }
                }
                false
            }

            reg.setOnClickListener {
                if (reg.isEnabled) {
                    loading2.visibility = View.VISIBLE
                    registerViewModel.registration(
                        login = login.text.toString(),
                        firstName = firstName.text.toString(),
                        secondName = secondName.text.toString(),
                        age = age.text.toString(),
                        password = password.text.toString()
                    )
                }
            }
        }

        secPassword.apply {
            afterTextChanged {
                registerViewModel.registerDataChanged(
                    login = login.text.toString(),
                    firstName = firstName.text.toString(),
                    secondName = secondName.text.toString(),
                    age = age.text.toString(),
                    password = password.text.toString(),
                    secPassword = secPassword.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                if (reg.isEnabled) {
                    when (actionId) {
                        EditorInfo.IME_ACTION_DONE ->
                            registerViewModel.registration(
                                login = login.text.toString(),
                                firstName = firstName.text.toString(),
                                secondName = secondName.text.toString(),
                                age = age.text.toString(),
                                password = password.text.toString()
                            )
                    }
                }
                false
            }
        }

    }

    private fun updateUiWithUser() {
        Toast.makeText(
            applicationContext,
            "Registration success",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showRegisterFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
}
