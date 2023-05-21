package com.example.tastypizzaclient.retrofit.api

import com.example.tastypizzaclient.model.request.RegisterRequest
import com.example.tastypizzaclient.model.response.AuthResponse
import com.example.tastypizzaclient.model.response.ProfileResponse
import com.example.tastypizzaclient.model.response.RegisterResponse
import com.example.tastypizzaclient.model.response.TokenResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface AuthApi {
    @POST("auth/sign-in")
    fun signIn(@Query("email") email: String): Call<AuthResponse>

    @POST("auth/verification")
    fun verify(
        @Header("Authorization") authToken: String,
        @Query("verificationCode") code: String
    ): Call<TokenResponse>

    @POST("auth/refresh-token")
    fun refreshToken(@Query("id") refreshToken: String): Call<TokenResponse>

    @POST("auth/sign-up")
    fun signUp(@Body registerRequest: RegisterRequest): Call<RegisterResponse>

    @GET("profile")
    fun getProfile(@Header("Authorization") accessToken: String): Call<ProfileResponse>
}