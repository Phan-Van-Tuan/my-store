package com.jpatrick.mystore.data.api

import com.jpatrick.mystore.data.model.dto.DetailProductDto
import com.jpatrick.mystore.data.model.dto.LoginDto
import com.jpatrick.mystore.data.model.dto.RegisterDto
import com.jpatrick.mystore.data.model.User
import com.jpatrick.mystore.data.model.Product
import com.jpatrick.mystore.data.model.ApiResponse
import com.jpatrick.mystore.data.model.Order
import com.jpatrick.mystore.data.model.dto.CartDto
import com.jpatrick.mystore.data.model.dto.CreateProductDto
import com.jpatrick.mystore.data.model.dto.LoggedDto
import com.jpatrick.mystore.data.model.dto.RefreshToken
import com.jpatrick.mystore.data.model.dto.ProductIdAndQuantity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

// API Service Interface
interface ApiService {
    @POST("auth/signup")
    suspend fun register(@Body registerDto: RegisterDto): Response<ApiResponse<List<User>>>

    @POST("auth/signin")
    suspend fun login(@Body loginDto: LoginDto): Response<ApiResponse<LoggedDto>>

    @GET("auth/my-profile")
    suspend fun getMyProfile(@Header("Authorization") authHeader: String): Response<ApiResponse<User>>

    @POST("auth/signout")
    suspend fun logout(@Header("Authorization") authHeader: String, @Body refreshToken: RefreshToken): Response<ApiResponse<Boolean>>

    @POST("auth/refresh-token")
    suspend fun refreshToken(@Body refreshToken: RefreshToken): Response<ApiResponse<LoggedDto>>

    @GET("cart")
    suspend fun getCart(@Header("Authorization") authHeader: String): Response<ApiResponse<List<CartDto>>>

    @POST("cart/")
    suspend fun updateProductToCart(
        @Header("Authorization") authHeader: String,
        @Body data: ProductIdAndQuantity // Đối tượng chứa productId và quantity
    ): Response<ApiResponse<List<CartDto>>>

    // Get a list of products
    @GET("product")
    suspend fun getProducts(
        @Query("limit") limit: Int? = null,
        @Query("offset") offset: Int? = null
    ): Response<ApiResponse<List<Product>>>

    // Get details of a single product
    @GET("product/{id}")
    suspend fun getProductById(
        @Path("id") productId: String
    ): Response<ApiResponse<DetailProductDto>>

    @GET("product/get-by-store-id/{id}")
    suspend fun getProductByStoreId(@Path("id") storeId: String): Response<ApiResponse<List<Product>>>

    @DELETE("product/{storeId}/{productId}")
    suspend fun deleteProduct(
        @Header("Authorization") authHeader: String,
        @Path("storeId") storeId: String,
        @Path("productId") productId: String
    ): Response<ApiResponse<List<Product>>>


    // Create a new product (admin functionality)
    @POST("product")
    suspend fun createProduct(
        @Header("Authorization") authHeader: String,
        @Body product: CreateProductDto
    ): Response<ApiResponse<List<Product>>>

    @PUT("product/{id}")
    suspend fun updateProduct(
        @Header("Authorization") authHeader: String,
        @Path("id") productId: String,
        @Body data: Product // Đối tượng chứa productId và quantity
    ): Response<ApiResponse<List<Product>>>


    @POST("order")
    suspend fun createOrder(
        @Header("Authorization") authHeader: String,
        @Body order: Order
    ): Response<ApiResponse<Order>>
}