package com.example.tastypizzaclient.retrofit.api

import com.example.tastypizzaclient.model.request.CreateOrderRequest
import com.example.tastypizzaclient.model.response.MenuItemResponse
import com.example.tastypizzaclient.model.response.ProfileResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface MenuApiService {
    @GET("menu/")
    fun getMenuItems(): Call<List<MenuItemResponse>>

    @GET("orders/check-menu-item")
    fun checkMenuItem(
        @Query("menuItemOptionId") menuItemOptionId: Int,
        @Query("restaurantId") restaurantId: Int
    ): Call<Unit>

    @POST("orders")
    fun createOrder(
        @Header("Authorization") token: String,
        @Body request: CreateOrderRequest): Call<Int>
}