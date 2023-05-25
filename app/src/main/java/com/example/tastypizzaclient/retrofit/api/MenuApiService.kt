package com.example.tastypizzaclient.retrofit.api

import com.example.tastypizzaclient.model.response.MenuItemResponse
import com.example.tastypizzaclient.model.response.ProfileResponse
import retrofit2.Call
import retrofit2.http.GET

interface MenuApiService {
    @GET("menu/")
    fun getMenuItems(): Call<List<MenuItemResponse>>
}