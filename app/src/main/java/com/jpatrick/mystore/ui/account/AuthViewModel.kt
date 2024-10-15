package com.jpatrick.mystore.ui.account

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jpatrick.mystore.data.model.dto.LoginDto
import com.jpatrick.mystore.data.model.dto.RegisterDto
import com.jpatrick.mystore.data.model.User
import com.jpatrick.mystore.data.model.Entity.TokenEntity
import com.jpatrick.mystore.data.source.local.AuthRepository
import com.jpatrick.mystore.data.source.remote.AuthRemote
import kotlinx.coroutines.launch

// AuthViewModel.kt
class AuthViewModel(val context: Context) : ViewModel() {
    private val remote = AuthRemote()
    private val repository = AuthRepository(context)

    // Register -----------------------
    private val _registerResult = MutableLiveData<Result<User?>>()
    val registerResult: LiveData<Result<User?>> get() = _registerResult

    val rUsername = MutableLiveData<String>()
    val rPassword = MutableLiveData<String>()
    val rEmail = MutableLiveData<String>()
    val rConfirmPassword = MutableLiveData<String>()

    fun onRegisterClicked() {
        val usernameValue = rUsername.value.toString().trim()
        val emailValue = rEmail.value.toString().trim()
        val passwordValue = rPassword.value.toString().trim()
        val confirmPasswordValue = rConfirmPassword.value.toString().trim()

        when {

            !isValidUsername(usernameValue) -> showToast("Invalid username $usernameValue")
            !isValidEmail(emailValue) -> showToast("Invalid email $emailValue")
            !isValidPassword(passwordValue) -> showToast("Invalid password")
            passwordValue != confirmPasswordValue -> showToast("Passwords do not match")
            else -> {
                register(RegisterDto(usernameValue, emailValue, passwordValue))
            }
        }
    }

    private fun register(registerDto: RegisterDto) {
        viewModelScope.launch {
            val result = remote.register(registerDto)
            _registerResult.value = result
        }
    }

    // Login -------------------------------
    private val _loginResult = MutableLiveData<Result<String>>()
    val loginResult: LiveData<Result<String>> get() = _loginResult

    fun login(loginDto: LoginDto) {
        // Validate email and password
        if (!isValidEmail(loginDto.email) || !isValidPassword(loginDto.password)) {
            _loginResult.value = Result.failure(Exception("Invalid login credentials"))
            return
        }

        // Launch coroutine to handle login request
        viewModelScope.launch {
            try {
                val result = remote.login(loginDto)
                result.onSuccess {
                    val tokenEntity = TokenEntity(accessToken = it.accessToken, refreshToken = it.refreshToken)
                    repository.saveToken(tokenEntity)
                    _loginResult.value = Result.success("Login successfully")
                }.onFailure {
//                    _loginResult.value = Result.failure(Exception("Login failed"))
                    _loginResult.value = Result.failure(it)
                }
            } catch (e: Exception) {
                _loginResult.value = Result.failure(e)
            }
        }
    }

    // Helper functions -------------------------
    private fun isValidUsername(username: String): Boolean {
        val usernameRegex = "^[a-zA-Z0-9_.-]+$".toRegex()
        return username.matches(usernameRegex)
    }

    private fun isValidEmail(email: String): Boolean {
        val regexEmail = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$".toRegex()
        return email.matches(regexEmail)
    }

    // Mật khẩu (ít nhất 8 ký tự, bao gồm ít nhất một chữ cái viết hoa, một chữ cái viết thường và một số):
    /*
    ^[a-zA-Z0-9_.-]+: Bắt đầu với chữ cái, số hoặc các ký tự _, ., -.
    (?: [a-zA-Z0-9_.-]+)*: Cho phép khoảng trắng và tiếp theo là các ký tự chữ cái, số, hoặc các ký tự _, ., -. Điều này cho phép nhiều từ cách nhau bởi khoảng trắng.
    * cho phép khoảng trắng xuất hiện nhiều lần giữa các từ.
    */
    private fun isValidPassword(password: String): Boolean {
        // Password must be at least 8 characters, contain uppercase, lowercase, number, and special character
        val passwordRegex =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#\$%^&+=!])[A-Za-z\\d@#\$%^&+=!]{8,}$".toRegex()
        return password.matches(passwordRegex)
    }


    val message = MutableLiveData<String>()
    private fun showToast(m: String) {
        message.value = m
    }
}



