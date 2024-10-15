package com.jpatrick.mystore.data.model

data class Feedback(
    val _id: String,
    val productId: String,
    val userId: String,
    val userName: String,
    val userAvatar: String,
    val rating: Number,
    val comment: String,
    val createdAt: String,
)
