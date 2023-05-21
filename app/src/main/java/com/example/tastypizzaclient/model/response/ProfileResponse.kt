package com.example.tastypizzaclient.model.response

data class ProfileResponse(
    val id: Long = 0L,
    val email: String = "",
    val verificationCode: String = "",
    val isMale: Boolean = false,
    val firstName: String = "",
    val lastName: String = "",
    val phoneNumber: String = "",
    val birthday: String = "",
    val tastyCoins: Long = 0L
)
