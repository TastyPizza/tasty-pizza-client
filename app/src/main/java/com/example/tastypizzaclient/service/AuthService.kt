package com.example.tastypizzaclient.service

import android.util.Log
import com.example.tastypizzaclient.model.request.RegisterRequest
import com.example.tastypizzaclient.model.response.AuthResponse
import com.example.tastypizzaclient.model.response.RegisterResponse
import com.example.tastypizzaclient.retrofit.api.AuthApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AuthService {
    private val baseURL: String = "http://158.160.23.54:21400/"
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseURL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val authApi: AuthApi = retrofit.create(AuthApi::class.java)

    fun signIn(email: String): AuthResponse {
        val authResponse = AuthResponse()
        authApi.signIn(email = email).enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                authResponse.jwt = response.body()!!.jwt
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.e("Error", "Что-то пошло не так", t)
            }
        })
        return authResponse
    }

    fun signUp(registerRequest: RegisterRequest): RegisterResponse{
        val registerResponse = RegisterResponse()
        return registerResponse
    }
}