package com.jpatrick.mystore.data.model.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tokens")
data class TokenEntity(
    @PrimaryKey val id: Int = 0,
    val accessToken: String,
    val refreshToken: String,
    val expiresAt: Long = System.currentTimeMillis() + 24 * 60 * 60 * 1000
)