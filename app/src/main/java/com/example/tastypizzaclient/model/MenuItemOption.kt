package com.example.tastypizzaclient.model

data class MenuItemOption(
    val count: Int,
    val name: String,
    val pictureId: String,
    val price: Int,
    val weight: Int,
    val nutritional: Int,
    val proteins: Int,
    val fats: Int,
    val carbohydrates: Int,
    val traditionalDough: Boolean,
    val id: Int,
    var menuItemId: Int
) {
    override fun toString(): String {
        return "$name, $weight г\nКБЖУ:\t$nutritional\t$proteins\t$fats\t$carbohydrates"
    }
}