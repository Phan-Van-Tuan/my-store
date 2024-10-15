package com.jpatrick.mystore.data.source.remote

import android.content.Context
import com.jpatrick.mystore.data.api.ApiService
import com.jpatrick.mystore.data.api.ServiceBuilder
import com.jpatrick.mystore.data.model.dto.LoginDto
import com.jpatrick.mystore.data.model.dto.RegisterDto
import com.jpatrick.mystore.data.model.ApiResponse
import com.jpatrick.mystore.data.model.Entity.TokenEntity
import com.jpatrick.mystore.data.model.User
import com.jpatrick.mystore.data.model.dto.LoggedDto
import com.jpatrick.mystore.data.model.dto.RefreshToken
import com.jpatrick.mystore.data.source.local.AuthRepository

class AuthRemote() {
    private val apiService: ApiService = ServiceBuilder.buildService(ApiService::class.java)

    suspend fun register(registerDto: RegisterDto): Result<User?> {
        val response = apiService.register(registerDto)
        return if (response.isSuccessful) {
            Result.success(response.body()?.data?.get(0))
        } else {
            // Xử lý lỗi
            Result.failure(Exception("Đăng ký thất bại: ${response.errorBody()?.string()}"))
        }
    }

    suspend fun login(loginDto: LoginDto): Result<LoggedDto> {
        val response = apiService.login(loginDto)
        return if (response.isSuccessful) {
            val t = response.body() as ApiResponse
            if (t.status == "Success") {
                Result.success(t.data as LoggedDto)
            } else {
                Result.failure(Exception("Đăng nhập thất bại: ${t.error}"))
            }
        } else {
            // Xử lý lỗi khi phản hồi không thành công
            Result.failure(Exception("Đăng nhập thất bại: ${response.errorBody()?.string()}"))
        }
    }

    suspend fun getMyProfile(token: String): Result<User> {
        val response = apiService.getMyProfile(token)
        return if (response.isSuccessful) {
            val t = response.body() as ApiResponse
            if (t.status == "Success") {
                Result.success(t.data as User)
            } else {
                Result.failure(Exception("Thất bại: ${t.error}"))
            }
        } else {
            Result.failure(Exception("Thất bại: ${response.errorBody()?.string()}"))
        }
    }

    suspend fun logout(accessToken: String, refreshToken: String): Result<String> {
        val response = apiService.logout(accessToken, RefreshToken(refreshToken))
        return if (response.isSuccessful) {
            val t = response.body() as ApiResponse
            if (t.status == "Success") {
                Result.success(t.message)
            } else {
                Result.failure(Exception("Thất bại: ${t.error}"))
            }
        } else {
            Result.failure(Exception("Thất bại: ${response.errorBody()?.string()}"))
        }
    }

    suspend fun refreshToken(refreshToken: RefreshToken): Result<LoggedDto> {
        val response = apiService.refreshToken(refreshToken)
        return if (response.isSuccessful) {
            val t = response.body() as ApiResponse
            if (t.status == "Success") {
                Result.success(t.data as LoggedDto)
            } else {
                Result.failure(Exception("Token refresh failed: ${t.error}"))
            }
        } else {
            Result.failure(Exception("Token refresh failed"))
        }
    }


}