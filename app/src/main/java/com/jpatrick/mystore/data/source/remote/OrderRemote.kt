package com.jpatrick.mystore.data.source.remote

import com.jpatrick.mystore.data.api.ApiService
import com.jpatrick.mystore.data.api.ServiceBuilder
import com.jpatrick.mystore.data.model.Product

class OrderRemote {
    private val apiService: ApiService = ServiceBuilder.buildService(ApiService::class.java)
    suspend fun getAllOrderById(): List<Product>? {
        val response = apiService.getProducts()
        return if (response.isSuccessful) {
            response.body()?.data
        } else {
            // Xử lý lỗi
            null
        }
    }
}