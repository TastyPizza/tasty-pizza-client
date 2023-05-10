package com.example.tastypizzaclient.retrofit.api

import com.example.tastypizzaclient.model.response.AuthResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthApi {
    @POST("auth/sign-in")
    fun signIn(@Query("email") email: String) : Call<AuthResponse>
}