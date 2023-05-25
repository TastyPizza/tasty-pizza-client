package com.example.tastypizzaclient.model.response

data class MenuItemResponse(
    val type: String,
    val newBadge: Boolean,
    val name: String,
    val description: String,
    val id: Int
)