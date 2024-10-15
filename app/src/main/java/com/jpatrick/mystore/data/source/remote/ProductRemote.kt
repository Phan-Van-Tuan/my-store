package com.jpatrick.mystore.data.source.remote

import com.jpatrick.mystore.data.api.ApiService
import com.jpatrick.mystore.data.api.ServiceBuilder
import com.jpatrick.mystore.data.model.dto.DetailProductDto
import com.jpatrick.mystore.data.model.Product

class ProductRemote {

    private val apiService: ApiService = ServiceBuilder.buildService(ApiService::class.java)

    suspend fun getAllProducts(limit: Int, offset: Int): List<Product>? {
        val response = apiService.getProducts(limit, offset)
        return if (response.isSuccessful) {
            response.body()?.data
        } else {
            // Xử lý lỗi
            null
        }
    }

    suspend fun getProductDetails(id: String): DetailProductDto? {
        val response = apiService.getProductById(id)
        return if (response.isSuccessful) {
            response.body()?.data
        } else {
            // Xử lý lỗi
            null
        }
    }
}
