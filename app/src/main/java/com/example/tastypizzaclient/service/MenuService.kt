package com.example.tastypizzaclient.service

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.example.tastypizzaclient.model.MenuItem
import com.example.tastypizzaclient.model.response.MenuItemResponse
import com.example.tastypizzaclient.retrofit.api.MenuApiService
import kotlinx.coroutines.async
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MenuService {
    private val baseURL: String = "http://158.160.23.54:8080/"
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseURL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val menuApi: MenuApiService = retrofit.create(MenuApiService::class.java)


    fun getMenuItems(callback: (List<MenuItem>) -> Unit) {
        menuApi.getMenuItems().enqueue(object : Callback<List<MenuItemResponse>> {
            override fun onResponse(
                call: Call<List<MenuItemResponse>>,
                response: Response<List<MenuItemResponse>>
            ) {
                val menuItems = response.body()?.map { menuItemResponse ->
                    MenuItem(menuItemResponse.name, menuItemResponse.description, "от 199 ₽", menuItemResponse.type)
                } ?: emptyList()
                callback(menuItems)
            }

            override fun onFailure(call: Call<List<MenuItemResponse>>, t: Throwable) {
                Log.e("Error", "Что-то пошло не так", t)
                callback(emptyList())
            }
        })
    }

}

