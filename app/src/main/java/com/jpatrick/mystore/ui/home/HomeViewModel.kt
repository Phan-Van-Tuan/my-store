package com.jpatrick.mystore.ui.home

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jpatrick.mystore.data.model.Entity.TokenEntity
import com.jpatrick.mystore.data.model.Product
import com.jpatrick.mystore.data.model.dto.RefreshToken
import com.jpatrick.mystore.data.source.local.AuthRepository
import com.jpatrick.mystore.data.source.remote.AuthRemote
import com.jpatrick.mystore.data.source.remote.ProductRemote
import kotlinx.coroutines.launch
import kotlin.contracts.contract

class HomeViewModel(context: Context) : ViewModel() {

    private val productRemote = ProductRemote()
    private val authRepo = AuthRepository(context)
    private val authRemote = AuthRemote()

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
                val result = productRemote.getAllProducts(limit = 20, offset = 0)
                _data.value = result
            } catch (e: Exception) {
                // Xử lý lỗi khi gọi API (nếu có)
//                Log.e("HomeViewModel", "Error fetching data", e)
            }
        }
    }

    fun checkTokenAndRefresh() {
        viewModelScope.launch {
            val token = authRepo.getToken()
            token?.let {
                if (authRepo.isTokenExpired(it)) {
                    // Token hết hạn, thực hiện refresh
                    val result = authRemote.refreshToken(RefreshToken(it.refreshToken))
                    result.onSuccess { loggedDto ->
                        val tokenEntity = TokenEntity(accessToken = loggedDto.accessToken, refreshToken = loggedDto.refreshToken)
                        authRepo.saveToken(tokenEntity)
                    }.onFailure {

                    }
                }
            }
        }
    }
}