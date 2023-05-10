package com.example.tastypizzaclient.util

import android.util.Patterns

class Validator {

    fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidName(name: String): Boolean {
        val regex = Regex("^[а-яА-ЯёЁ\\s]+\$")
        return regex.matches(name)
    }
}