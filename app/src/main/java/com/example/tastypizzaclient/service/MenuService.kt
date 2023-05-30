package com.example.tastypizzaclient.service

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.example.tastypizzaclient.MainActivity
import com.example.tastypizzaclient.model.CartItem
import com.example.tastypizzaclient.model.MenuItem
import com.example.tastypizzaclient.model.MenuItemOption
import com.example.tastypizzaclient.model.request.CreateOrderRequest
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
                    MenuItem(menuItemResponse.id,menuItemResponse.name, menuItemResponse.description, "от 199 ₽", 199 ,menuItemResponse.type)
                } ?: emptyList()
                for (item: MenuItemResponse in response.body()!!){
                    var minPrice: Int = 99999999
                    for (option: MenuItemOption in item.menuItemOptions){
                        option.menuItemId = item.id
                        if (option.price < minPrice) minPrice = option.price
                        val existingItem = menuItemOptions.find { it.id == option.id }
                        if (existingItem == null) menuItemOptions.add(option)
                    }
                    menuItems.forEach { if (item.id == it.id) it.price = "от ${minPrice} ₽"}
                }
                callback(menuItems)
            }

            override fun onFailure(call: Call<List<MenuItemResponse >>, t: Throwable) {
                Log.e("Error", "Что-то пошло не так", t)
                callback(emptyList())
            }
        })
    }

    fun checkMenuItem(menuItemOptionId: Int, restaurantId: Int, callback: (Int) -> Unit) {
        menuApi.checkMenuItem(menuItemOptionId, restaurantId).enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                val statusCode = response.code()
                callback(statusCode)
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Log.e("Error", "Что-то пошло не так", t)
                callback(-1) // Обработка ошибки, если запрос не удался
            }
        })
    }

    fun createOrder(request: CreateOrderRequest, callback: (Pair<Int?, Int?>) -> Unit) {
        println("отправляем запрос на размещение заказа с токеном: ${MainActivity.accessToken}")
        menuApi.createOrder(MainActivity.accessToken, request).enqueue(object : Callback<Int> {
            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                val orderId = response.body()
                val statusCode = response.code()
                callback(Pair(orderId, statusCode))
            }

            override fun onFailure(call: Call<Int>, t: Throwable) {
                Log.e("Error", "Что-то пошло не так", t)
                callback(Pair(null, -1))
            }
        })
    }





    companion object {
        private val menuItemOptions: MutableList<MenuItemOption> = mutableListOf()

        fun getMenuItemOptions(): List<MenuItemOption>{
            return menuItemOptions
        }
    }

}

