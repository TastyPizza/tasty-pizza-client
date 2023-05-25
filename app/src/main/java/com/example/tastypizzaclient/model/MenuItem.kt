package com.example.tastypizzaclient.model

import java.io.Serializable

data class MenuItem(
    val title: String,
    val description: String,
    val price: String,
    val type: String
    ) : Serializable
