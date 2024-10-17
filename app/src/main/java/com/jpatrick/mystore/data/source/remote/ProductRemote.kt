package com.jpatrick.mystore.data.source.remote

import com.jpatrick.mystore.data.api.ApiService
import com.jpatrick.mystore.data.api.ServiceBuilder
import com.jpatrick.mystore.data.model.dto.DetailProductDto
import com.jpatrick.mystore.data.model.Product
import com.jpatrick.mystore.data.model.dto.CreateProductDto

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

    suspend fun getProductByStoreId(storeId: String): Result<List<Product>> {
        val response = apiService.getProductByStoreId(storeId)
        return if (response.isSuccessful) {
            Result.success(response.body()?.data as List<Product>)
        } else {
            Result.failure(Exception("Lỗi: ${response.errorBody()?.string()}"))
        }
    }

    suspend fun createProduct(
        authHeader: String,
        product: CreateProductDto
    ): Result<List<Product>> {
        val response = apiService.createProduct(authHeader, product)
        return if (response.isSuccessful) {
            Result.success(response.body()?.data as List<Product>)
        } else {
            Result.failure(Exception("Lỗi: ${response.errorBody()?.string()}"))
        }
    }

    suspend fun deleteProduct(
        authHeader: String,
        storeId: String,
        productId: String,
        ): Result<List<Product>> {
        val response = apiService.deleteProduct(authHeader, storeId, productId)
        return if (response.isSuccessful) {
            Result.success(response.body()?.data as List<Product>)
        } else {
            Result.failure(Exception("Lỗi: ${response.errorBody()?.string()}"))
        }
    }

    suspend fun updateProduct(
        authHeader: String,
        productId: String,
        data: Product
    ): Result<List<Product>> {
        val response = apiService.updateProduct(authHeader, productId, data)
        return if (response.isSuccessful) {
            Result.success(response.body()?.data as List<Product>)
        } else {
            Result.failure(Exception("Lỗi: ${response.errorBody()?.string()}"))
        }
    }
}
