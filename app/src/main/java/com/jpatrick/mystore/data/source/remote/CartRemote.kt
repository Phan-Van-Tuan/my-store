package com.jpatrick.mystore.data.source.remote

import com.jpatrick.mystore.data.api.ApiService
import com.jpatrick.mystore.data.api.ServiceBuilder
import com.jpatrick.mystore.data.model.Cart
import com.jpatrick.mystore.data.model.dto.CartDto
import com.jpatrick.mystore.data.model.dto.UpdateProductToCart

class CartRemote {
    private val apiService: ApiService = ServiceBuilder.buildService(ApiService::class.java)

    suspend fun getCart(accessToken: String): Result<List<CartDto>> {
        val response = apiService.getCart(accessToken)
        return if (response.isSuccessful) {
            Result.success(response.body()?.data as List<CartDto>)
        } else {
            Result.failure(Exception("Lỗi: ${response.errorBody()?.string()}"))
        }
    }

    suspend fun updateProductToCart(authHeader: String, data: UpdateProductToCart): Result<List<CartDto>> {
        val response = apiService.updateProductToCart(authHeader, data)
        return if (response.isSuccessful) {
            Result.success(response.body()?.data as List<CartDto>)
        } else {
            Result.failure(Exception("Lỗi: ${response.errorBody()?.string()}"))
        }
    }
}
