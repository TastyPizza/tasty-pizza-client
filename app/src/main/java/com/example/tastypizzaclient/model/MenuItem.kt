package com.example.tastypizzaclient.model

import java.io.Serializable

data class MenuItem(
    val id: Int,
    val title: String,
    val description: String,
    var price: String,
    val priceInt: Int,
    val type: String,
    ) : Serializable

