package com.jpatrick.mystore.data.model

data class User(
    val _id: String,
    val username: String,
    val email: String,
    val password: String,
    val bio: String,
    val image: String,
    val role: String,
    val isVerify: Boolean,
    val createdAt: String,
)
