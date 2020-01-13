package com.handtruth.javaschool.ui.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.handtruth.javaschool.R
import com.handtruth.javaschool.data.*

class RegisterViewModel(private val registerRepository: RegisterRepository) : ViewModel() {
    private val _registerForm = MutableLiveData<RegisterFormState>()
    val registerFormState = _registerForm

    private val _registerResult = MutableLiveData<RegisterResult>()
    val registerResult = _registerResult


    fun registration(firstName: String,
                     secondName: String, age: String, login: String, password: String ) {
        registerRepository.registration(firstName, secondName, age, login, password, _registerResult)
    }


    fun registerDataChanged(firstName: String,
                            secondName: String, age: String, login: String, password: String, secPassword: String ) {
        var isDataValid = true
        if (!isUserNameValid(login) && login != "") {
            _registerForm.value = RegisterFormState(usernameError = R.string.invalid_username)
            isDataValid = false
        }
        if (!isPasswordValid(password) && password != "") {
            _registerForm.value = RegisterFormState(passwordError = R.string.invalid_password)
            isDataValid = false
        }
        if (!isPasswordEql(password, secPassword) && password != "" && secPassword != ""){
            _registerForm.value = RegisterFormState(passwordSecError = R.string.not_eql_password)
            isDataValid = false
        }
        if (age.toIntOrNull() == null && age != "") {
            _registerForm.value = RegisterFormState(ageError = R.string.not_a_number)
            isDataValid = false
        }
        if (firstName == "" || secondName == "" || age == "" ||
            login == "" || password == "" || secPassword == "") {
            isDataValid = false
        }
        if (isDataValid) {
            _registerForm.value = RegisterFormState(isDataValid = true)
        }
    }
}