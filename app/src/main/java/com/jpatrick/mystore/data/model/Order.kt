package com.jpatrick.mystore.data.model

import java.util.*

data class Order(
    val _id: String?, // ID của đơn hàng (có thể dùng ObjectId dưới dạng String)
    val userId: String, // ID của người dùng
    val products: List<Product>, // Danh sách sản phẩm trong đơn hàng
    val freeship: String?, // ID của voucher freeship (nullable)
    val discount: String?, // ID của voucher giảm giá (nullable)
    val totalAmount: Double, // Tổng số tiền của đơn hàng
    val note: String?, // Ghi chú của khách hàng
    val status: String?, // Trạng thái đơn hàng
    val paymentStatus: String?,
    val paymentMethod: String,
    val createdAt: Date?, // Ngày tạo đơn hàng

)
