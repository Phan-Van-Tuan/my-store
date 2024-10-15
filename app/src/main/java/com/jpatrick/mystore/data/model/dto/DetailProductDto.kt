package com.jpatrick.mystore.data.model.dto

import com.jpatrick.mystore.data.model.Feedback
import com.jpatrick.mystore.data.model.Product
import com.jpatrick.mystore.data.model.User

data class DetailProductDto(
    val product: Product,
    val store: User,
    val feedbacks: List<Feedback>
)
