package com.example.tastypizzaclient.service

import android.media.session.MediaSession.Token
import android.provider.ContactsContract.Profile
import android.util.Log
import com.example.tastypizzaclient.model.request.RegisterRequest
import com.example.tastypizzaclient.model.response.AuthResponse
import com.example.tastypizzaclient.model.response.ProfileResponse
import com.example.tastypizzaclient.model.response.RegisterResponse
import com.example.tastypizzaclient.model.response.TokenResponse
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

    fun signIn(email: String, callback: (AuthResponse) -> Unit) {
        var authResponse = AuthResponse()
        authApi.signIn(email = email).enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                Log.d("Code", response.code().toString())
                authResponse.jwt = response.body()?.jwt.toString()
                authResponse.errorMessage = response.code().toString()
                callback(authResponse)
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.e("Error", "Что-то пошло не так", t)
                callback(authResponse)
            }
        })
    }

    fun verify(authToken: String, code: String, callback: (TokenResponse) -> Unit) {
        var tokenResponse = TokenResponse()
        authApi.verify(authToken = authToken, code = code)
            .enqueue(object : Callback<TokenResponse> {
                override fun onResponse(
                    call: Call<TokenResponse>,
                    response: Response<TokenResponse>
                ) {
                    tokenResponse.accessToken = response.body()?.accessToken.toString()
                    tokenResponse.refreshToken = response.body()?.refreshToken.toString()
                    tokenResponse.errorMessage = response.code().toString()
                    callback(tokenResponse)
                }

                override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                    Log.e("Error", "Что-то пошло не так", t)
                    tokenResponse.errorMessage = t.message.toString()
                    callback(tokenResponse)
                }
            })
    }

    fun signUp(registerRequest: RegisterRequest, callback: (RegisterResponse) -> Unit) {
        val registerResponse = RegisterResponse()
        authApi.signUp(registerRequest).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {

                registerResponse.jwt = response.body()?.jwt.toString()
                registerResponse.errorMessage = response.code().toString()
                callback(registerResponse)
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Log.e("Error", "Что-то пошло не так", t)
                callback(registerResponse)
            }
        })
        callback(registerResponse)
    }

    fun refreshTokens(refreshToken: String, callback: (TokenResponse) -> Unit) {
        val tokenResponse = TokenResponse()
        callback(tokenResponse)
    }

    fun getProfile(accessToken: String, callback: (ProfileResponse) -> Unit) {
        val profileResponse = ProfileResponse()
        callback(profileResponse)
    }
}