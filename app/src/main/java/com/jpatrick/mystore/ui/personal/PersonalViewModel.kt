package com.jpatrick.mystore.ui.personal

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jpatrick.mystore.data.model.Product
import com.jpatrick.mystore.data.model.User
import com.jpatrick.mystore.data.source.local.AuthRepository
import com.jpatrick.mystore.data.source.remote.AuthRemote
import kotlinx.coroutines.launch
import kotlin.math.E

class PersonalViewModel(val context: Context) : ViewModel() {

    private val repository = AuthRepository(context)
    private val remote = AuthRemote()

    private val _data = MutableLiveData<Result<User>>()
    val data: LiveData<Result<User>> get() = _data

    fun fetchData(refresh: Boolean = false) {
        // Kiểm tra nếu không cần làm mới và đã có dữ liệu
        if (!refresh && _data.value != null) {
            // Dữ liệu đã có, không cần gọi API
            return
        }

        // Gọi API nếu chưa có dữ liệu
        viewModelScope.launch {
            try {
                val token = "Bearer ${repository.getToken()?.accessToken}"
                val result = remote.getMyProfile(token)
                result.onSuccess {
                    _data.value = Result.success(it)
                }.onFailure {
                    _data.value = Result.failure(Exception(it))
                }
            } catch (e: Exception) {
                _data.value = Result.failure(e)
            }

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

    fun logout() {
        viewModelScope.launch {
            try {
                val accessToken = "Bearer ${repository.getToken()?.accessToken}"
                val refreshToken = repository.getToken()?.refreshToken.toString()
                val result = remote.logout(accessToken, refreshToken)
                result.onSuccess {
                    _loginRequired.value = true
                }.onFailure {
                    _loginRequired.value = false
                }
                repository.clearTokens()
            } catch (e: Exception) {
                _loginRequired.value = false
            }

        }
    }
}