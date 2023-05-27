package com.example.tastypizzaclient.model.request

data class CreateOrderRequest(
    val clientId: Int,
    val restaurantId: Int,
    val listOfOrderItemDto: List<OrderItemDto>
)