package com.jpatrick.mystore.data.model.dto

import com.jpatrick.mystore.data.model.Product

data class CartDto(
    val product: Product,
    val quantity: Int,
    var isChecked: Boolean = false
)
