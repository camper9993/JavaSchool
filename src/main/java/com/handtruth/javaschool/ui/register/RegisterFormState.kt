package com.handtruth.javaschool.ui.register

data class RegisterFormState (
    val usernameError: Int? = null,
    val passwordError: Int? = null,
    val passwordSecError: Int? = null,
    val ageError: Int? = null,
    val isDataValid: Boolean = false
)
