package com.example.tastypizzaclient.model.response

import com.example.tastypizzaclient.model.MenuItemOption

data class MenuItemResponse(
    val type: String,
    val newBadge: Boolean,
    val name: String,
    val description: String,
    val id: Int,
    val menuItemOptions: List<MenuItemOption>
)