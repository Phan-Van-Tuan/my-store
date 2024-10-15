package com.jpatrick.mystore.ui.cart

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jpatrick.mystore.data.model.Product
import com.jpatrick.mystore.data.model.dto.CartDto
import com.jpatrick.mystore.data.model.dto.UpdateProductToCart
import com.jpatrick.mystore.data.source.local.AuthRepository
import com.jpatrick.mystore.data.source.remote.CartRemote
import kotlinx.coroutines.launch

class CartViewModel(val context: Context) : ViewModel() {
    private val cartRemote = CartRemote()
    private val authRepo = AuthRepository(context)

    private val _data = MutableLiveData<List<CartDto>>()
    val data: LiveData<List<CartDto>> get() = _data

    fun fetchData(refresh: Boolean = false) {
        // Kiểm tra nếu không cần làm mới và đã có dữ liệu
        if (!refresh && _data.value != null) {
            // Dữ liệu đã có, không cần gọi API
            return
        }

        // Gọi API nếu chưa có dữ liệu
        viewModelScope.launch {
            val accessToken = "Bearer ${authRepo.getToken()?.accessToken}"
            val result = cartRemote.getCart(accessToken)
            result.onSuccess {
                _data.value = it
            }.onFailure {
                Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Kiểm tra đăng nhập
    private val _loginRequired = MutableLiveData<Boolean>()
    val loginRequired: LiveData<Boolean> get() = _loginRequired

    fun checkIfUserLoggedIn() {
        viewModelScope.launch {
            val token = authRepo.getToken()
//            Toast.makeText(context, token?.accessToken.toString(), Toast.LENGTH_SHORT).show()
            _loginRequired.value = token?.accessToken.isNullOrEmpty()
        }
    }


    fun updateProductToCart(data: UpdateProductToCart) {
        viewModelScope.launch {
            val accessToken = "Bearer ${authRepo.getToken()?.accessToken}"
            val result = cartRemote.updateProductToCart(accessToken, data)
            result.onSuccess {
                _data.value = it
            }.onFailure {
                Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }
}