package com.jpatrick.mystore.ui.account

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.jpatrick.mystore.R
import com.jpatrick.mystore.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)

        val factory = AuthViewModelFactory(this)
        viewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]

        viewModel.registerResult.observe(this) { result ->
            result.onSuccess { token ->
                Toast.makeText(this, "Register successfully", Toast.LENGTH_SHORT).show()
                var intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)
                finish()
            }.onFailure { error ->
                Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
                binding.inputEmail.text = null
                binding.inputPassword.text = null
            }
        }

        viewModel.message.observe(this) {
            Toast.makeText(this, "$it", Toast.LENGTH_SHORT).show()
        }

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.btnToSignIn.setOnClickListener {
            var intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}