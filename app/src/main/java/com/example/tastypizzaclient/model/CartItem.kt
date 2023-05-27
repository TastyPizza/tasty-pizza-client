package com.example.tastypizzaclient.model

data class CartItem(
    val menuItemId: Int,
    val menuOptionId: Int,
    val title: String,
    val description: String,
    val cost: Int,
    var count: Int,
    val type: String
) {
    val price: Int
        get() = cost * count
}
