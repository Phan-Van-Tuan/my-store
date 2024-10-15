package com.jpatrick.mystore.ui.detail

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jpatrick.mystore.data.model.dto.DetailProductDto
import com.jpatrick.mystore.data.model.dto.UpdateProductToCart
import com.jpatrick.mystore.data.source.local.AuthRepository
import com.jpatrick.mystore.data.source.remote.CartRemote
import com.jpatrick.mystore.data.source.remote.ProductRemote
import kotlinx.coroutines.launch

class DetailViewModel(context: Context) : ViewModel() {

    private val productRemote = ProductRemote()
    private val cartRemote = CartRemote()
    private val authRepo = AuthRepository(context)

    private val _data = MutableLiveData<DetailProductDto>()
    val data: LiveData<DetailProductDto> get() = _data

    fun fetchData(productId: String, refresh: Boolean = false) {
        // Kiểm tra nếu không cần làm mới và đã có dữ liệu
        if (!refresh && _data.value != null) {
            // Dữ liệu đã có, không cần gọi API
            return
        }

        // Gọi API để lấy dữ liệu mới
        viewModelScope.launch {
            try {
                val result = productRemote.getProductDetails(productId)
                _data.value = result
            } catch (e: Exception) {
                // Xử lý lỗi khi gọi API (nếu có)
//                Log.e("HomeViewModel", "Error fetching data", e)
            }
        }
    }

    fun updateProductToCart(data: UpdateProductToCart, context: Context) {
        viewModelScope.launch {
            try {
                val accessToken = "Bearer ${authRepo.getToken()?.accessToken}"
                val result = cartRemote.updateProductToCart(accessToken, data)
                result.onSuccess {
                    Toast.makeText(context, "okey", Toast.LENGTH_SHORT).show()
                    Result.success("okey")
                }.onFailure {
                    Toast.makeText(context, "${result.exceptionOrNull()}", Toast.LENGTH_SHORT).show()
                    Result.failure<String>(Exception("lỗi"))
                }
            } catch (e: Exception) {
                Toast.makeText(context, "$e", Toast.LENGTH_SHORT).show()
                Result.failure(e)
            }
        }
    }
}