package com.jpatrick.mystore.ui.notification

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jpatrick.mystore.data.model.Product
import com.jpatrick.mystore.data.source.local.AuthRepository
import com.jpatrick.mystore.data.source.remote.ProductRemote
import kotlinx.coroutines.launch

class NotificationsViewModel(val context: Context) : ViewModel() {
    private val repository = AuthRepository(context)
    private val remote = ProductRemote()

    private val _data = MutableLiveData<List<Product>>()
    val data: LiveData<List<Product>> get() = _data

    fun fetchData(refresh: Boolean = false) {
        // Kiểm tra nếu không cần làm mới và đã có dữ liệu
        if (!refresh && _data.value != null) {
            // Dữ liệu đã có, không cần gọi API
            return
        }

        // Gọi API nếu chưa có dữ liệu
        viewModelScope.launch {
            val result = remote.getAllProducts(limit = 30, offset = 0)
            _data.value = result
        }
    }

    // Kiểm tra đăng nhập
    private val _loginRequired = MutableLiveData<Boolean>()
    val loginRequired: LiveData<Boolean> get() = _loginRequired

    fun checkIfUserLoggedIn() {
        viewModelScope.launch {
            val token = repository.getToken()
            _loginRequired.value = token?.accessToken.isNullOrEmpty()
        }
    }
}