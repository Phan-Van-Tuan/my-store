package com.jpatrick.mystore.ui.store.myproduct

import android.content.Context
import android.os.Debug
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jpatrick.mystore.data.model.Product
import com.jpatrick.mystore.data.model.dto.CreateProductDto
import com.jpatrick.mystore.data.source.local.AuthRepository
import com.jpatrick.mystore.data.source.remote.ProductRemote
import kotlinx.coroutines.launch

class MyProductViewModel(private val context: Context) : ViewModel() {
    private val productRemote = ProductRemote()
    private val authRepo = AuthRepository(context)
    private val _data = MutableLiveData<List<Product>>()
    val data: LiveData<List<Product>> get() = _data
    fun fetchData(refresh: Boolean = false) {
        // Kiểm tra nếu không cần làm mới và đã có dữ liệu
        if (!refresh && _data.value != null) {
            // Dữ liệu đã có, không cần gọi API
            return
        }

        // Gọi API để lấy dữ liệu mới
        viewModelScope.launch {
            try {
                val result = productRemote.getProductByStoreId("66fa1608d74779e29bbddf64")
                result.onSuccess {
                    _data.value = it
                }.onFailure { e ->
                    Result.failure<Exception>(e)
                }
            } catch (e: Exception) {
                Result.failure<Exception>(e)
            }
        }
    }

    fun getAvailableProducts(): List<Product> {
        return data.value?.filter { it.quantity.toInt() - it.sold.toInt() > 0 } ?: emptyList()
    }

    fun getNotAvailableProducts(): List<Product> {
        return data.value?.filter { it.quantity.toInt() - it.sold.toInt() == 0 } ?: emptyList()
    }

    fun createProduct(product: CreateProductDto, callback: (Result<Any>) -> Unit) {
        viewModelScope.launch {
            try {
                val accessToken = "Bearer ${authRepo.getToken()?.accessToken}"
                val result = productRemote.createProduct(accessToken, product)
                result.onSuccess {
                    _data.value = it
                    callback(Result.success(it))  // Trả về kết quả thành công qua callback
                }.onFailure { e ->
                    callback(Result.failure(e))  // Trả về kết quả lỗi qua callback
                }
            } catch (e: Exception) {
                callback(Result.failure(e))  // Trả về ngoại lệ qua callback
            }
        }
    }

    fun updateProduct(productId: String, product: Product) {
        viewModelScope.launch {
            try {
                val accessToken = "Bearer ${authRepo.getToken()?.accessToken}"
                val result = productRemote.updateProduct(accessToken, productId, product)

                result.onSuccess {
                    _data.value = it
                }.onFailure { e ->
                    Result.failure<Exception>(e)
                }
            } catch (e: Exception) {
                Result.failure<Exception>(e)
            }
        }
    }

    fun deleteProduct(productId: String) {
        viewModelScope.launch {
            try {
                val accessToken = "Bearer ${authRepo.getToken()?.accessToken}"
                val result = productRemote.deleteProduct(
                    accessToken,
                    "66fa1608d74779e29bbddf64",
                    productId,
                )
                result.onSuccess {
                    _data.value = it
                }.onFailure { e ->
                    Result.failure<Exception>(e)
                }
            } catch (e: Exception) {
                Result.failure<Exception>(e)
                Toast.makeText(context,e.toString(),Toast.LENGTH_SHORT).show()
                Log.d("aaa",e.toString())
            }
        }
    }
}
