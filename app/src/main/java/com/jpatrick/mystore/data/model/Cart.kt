package com.jpatrick.mystore.data.model

data class Cart(
    val userId: String,
    val products: Array<AProduct>
)

data class AProduct (
    val productId: String,
    val quantity: Number
)
