package com.example.tastypizzaclient.model.request

data class OrderItemDto(
    val menuItemOptionId: Int,
    val count: Int
)