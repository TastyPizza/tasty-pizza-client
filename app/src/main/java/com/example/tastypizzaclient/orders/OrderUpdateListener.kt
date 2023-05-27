package com.example.tastypizzaclient.orders

import com.example.tastypizzaclient.model.CartItem
import com.example.tastypizzaclient.model.MenuItem

interface OrderUpdateListener {
    fun onOrderUpdated(item: CartItem)
}