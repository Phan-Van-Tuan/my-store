package com.jpatrick.mystore.data.model

data class ApiResponse<T>(
    val status: String,
    val message: String,
    val data: T? = null,          // Dữ liệu cho trường hợp thành công (nullable)
    val error: ErrorDetail? = null // Thông tin lỗi cho trường hợp thất bại (nullable)
)

data class ErrorDetail(
    val statusCode: Int
)
