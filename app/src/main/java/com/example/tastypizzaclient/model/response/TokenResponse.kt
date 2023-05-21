package com.example.tastypizzaclient.model.response

data class TokenResponse(
    var accessToken: String = "",
    var refreshToken: String = "",
    var errorMessage: String = ""
)
