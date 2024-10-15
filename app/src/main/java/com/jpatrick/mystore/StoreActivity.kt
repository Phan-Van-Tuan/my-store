package com.jpatrick.mystore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.jpatrick.mystore.databinding.ActivityStoreBinding

class StoreActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStoreBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment_activity_store)
        binding.btnBack.setOnClickListener {
            if (navController.currentDestination?.id == R.id.store_home) {
                // Nếu đang ở HomeFragment, chuyển về Activity khác
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish() // Đóng MainActivity
            } else {
                // Nếu không phải ở HomeFragment, điều hướng về HomeFragment
                navController.navigate(R.id.store_home)
            }
        }
    }
}