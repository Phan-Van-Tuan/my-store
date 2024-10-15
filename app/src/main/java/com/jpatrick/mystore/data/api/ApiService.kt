package com.jpatrick.mystore.data.api

import com.jpatrick.mystore.data.model.dto.DetailProductDto
import com.jpatrick.mystore.data.model.dto.LoginDto
import com.jpatrick.mystore.data.model.dto.RegisterDto
import com.jpatrick.mystore.data.model.User
import com.jpatrick.mystore.data.model.Product
import com.jpatrick.mystore.data.model.ApiResponse
import com.jpatrick.mystore.data.model.Cart
import com.jpatrick.mystore.data.model.dto.CartDto
import com.jpatrick.mystore.data.model.dto.LoggedDto
import com.jpatrick.mystore.data.model.dto.RefreshToken
import com.jpatrick.mystore.data.model.dto.UpdateProductToCart
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.Header
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
        @Body data: UpdateProductToCart // Đối tượng chứa productId và quantity
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

    // Create a new product (admin functionality)
    @POST("products")
    suspend fun createProduct(
        @Body product: Product
    ): Response<Product>

    // Get user details
    @GET("users/{id}")
    suspend fun getUserById(
        @Path("id") userId: Int
    ): Response<User>

    // Create a new user
    @POST("users")
    suspend fun createUser(
        @Body user: User
    ): Response<User>
}