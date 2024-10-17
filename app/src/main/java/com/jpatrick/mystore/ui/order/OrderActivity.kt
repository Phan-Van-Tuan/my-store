package com.jpatrick.mystore.ui.order


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jpatrick.mystore.R
import com.jpatrick.mystore.data.model.dto.CartDto
import com.jpatrick.mystore.databinding.ActivityOrderBinding

class OrderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment_activity_order)
        binding.btnBack.setOnClickListener {
            if (navController.currentDestination?.id == R.id.createOrderFragment) {
                finish() // Đóng MainActivity
            } else {
                // Nếu không phải ở HomeFragment, điều hướng về HomeFragment
                navController.navigate(R.id.createOrderFragment)
            }
        }

        val orderViewModel = ViewModelProvider(this)[CreateOrderViewModel::class.java]
        val jsonProductList = intent.getStringExtra("product_list")
        val gson = Gson()
        val type = object : TypeToken<List<CartDto>>() {}.type
        val productList: List<CartDto> = gson.fromJson(jsonProductList, type)
        orderViewModel.setItem( productList)
    }


}