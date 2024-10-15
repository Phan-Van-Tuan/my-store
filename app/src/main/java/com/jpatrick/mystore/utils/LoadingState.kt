package com.jpatrick.mystore.utils

import com.jpatrick.mystore.data.model.ApiResponse

sealed class LoadingState<out T> {
    data object Loading : LoadingState<Nothing>()
    data class Success<out T>(val data: ApiResponse<@UnsafeVariance T>) : LoadingState<T>()
    data class Error(val message: String) : LoadingState<Nothing>()
}