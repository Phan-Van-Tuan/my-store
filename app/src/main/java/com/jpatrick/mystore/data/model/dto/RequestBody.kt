package com.jpatrick.mystore.data.model.dto


data class RefreshToken(
    val refreshToken: String
)

data class UpdateProductToCart(
    val productId: String,
    val quantity: Int
)
