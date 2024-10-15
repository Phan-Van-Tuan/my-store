package com.jpatrick.mystore.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ServiceBuilder {

    // Base URL của API
    private const val BASE_URL = "http://192.168.207.1:3003/api/v1/"

    // Tạo HttpLoggingInterceptor để log các request/response
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // Tạo OkHttpClient với timeout và logging
    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(loggingInterceptor)
        .build()

    // Tạo Retrofit instance
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)  // Sử dụng OkHttpClient tùy chỉnh
        .addConverterFactory(GsonConverterFactory.create())  // Sử dụng Gson để parse JSON
        .build()

    // Hàm này giúp tạo một service API bất kỳ từ interface
    fun <T> buildService(serviceType: Class<T>): T {
        return retrofit.create(serviceType)
    }
}