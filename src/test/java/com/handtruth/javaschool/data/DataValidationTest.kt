package com.handtruth.javaschool.data

import org.junit.Test

import org.junit.Assert.*

class DataValidationTest {

    @Test
    fun isUserNameValid() {
        assertFalse(isUserNameValid("Hello world"))
    }

    @Test
    fun isPasswordValid() {
        assertTrue(isPasswordValid("asdf1111"))
        assertFalse(isPasswordValid("sdf333"))
    }

    @Test
    fun isPasswordEql() {
        assertTrue(isPasswordEql("asdf1111" , "asdf1111"))
        assertFalse(isPasswordEql("asdf1111" , "asdf111"))
    }
}