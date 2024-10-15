package com.jpatrick.mystore.data.source.local

import android.content.Context
import com.jpatrick.mystore.data.database.AppDatabase
import com.jpatrick.mystore.data.model.Entity.TokenEntity

class AuthRepository (val context: Context){

    private val database = AppDatabase.getDatabase(context)
    private val tokenDao = database.tokenDao()

    suspend fun getToken(): TokenEntity? {
        return tokenDao.getToken()
    }

    suspend fun saveToken(tokenEntity: TokenEntity) {
        tokenDao.insertToken(tokenEntity)
    }

    suspend fun clearTokens() {
        tokenDao.deleteToken()
    }

    suspend fun isTokenExpired(token: TokenEntity): Boolean {
        return System.currentTimeMillis() > token.expiresAt
    }
}
