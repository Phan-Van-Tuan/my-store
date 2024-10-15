package com.jpatrick.mystore.ui.account

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.jpatrick.mystore.MainActivity
import com.jpatrick.mystore.data.model.dto.LoginDto
import com.jpatrick.mystore.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = AuthViewModelFactory(this)
        viewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]

        viewModel.loginResult.observe(this) { result ->
            result.onSuccess { message ->
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                var intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }.onFailure { error ->
                Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnLogin.setOnClickListener{
            val email = binding.inputEmail.text.toString().trim()
            val password = binding.inputPassword.text.toString().trim()
            viewModel.login(LoginDto(email, password))
        }

        binding.btnToSignup.setOnClickListener {
            var intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }


    }
}