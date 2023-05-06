package com.example.tastypizzaclient.model

data class RegisterModel(
    val email: String,
    val isMale: Boolean = true,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val birthday: String = "1970-01-01"
)
