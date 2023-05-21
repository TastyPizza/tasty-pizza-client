package com.example.tastypizzaclient.model.response

data class ProfileResponse(
    var id: Long = 0L,
    var email: String = "",
    var verificationCode: String = "",
    var isMale: Boolean = false,
    var firstName: String = "",
    var lastName: String = "",
    var phoneNumber: String = "",
    var birthday: String = "",
    var tastyCoins: Long = 0L
)
