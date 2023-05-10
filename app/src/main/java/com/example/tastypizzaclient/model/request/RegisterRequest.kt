package com.example.tastypizzaclient.model.request

data class RegisterRequest(
    val email: String,
    val isMale: Boolean = true,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val birthday: String = "1970-01-01"
)
