package com.jpatrick.mystore.data.model.dto


data class RefreshToken(
    val refreshToken: String
)

data class ProductIdAndQuantity(
    val productId: String,
    val quantity: Int
)

data class CreateProductDto(
    val img: String,
    val name: String,
    val description: String,
    val price: Number,
    val quantity: Number,
    val categories: Array<String>
)
