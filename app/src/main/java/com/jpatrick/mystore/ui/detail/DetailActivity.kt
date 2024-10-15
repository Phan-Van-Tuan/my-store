package com.jpatrick.mystore.ui.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.jpatrick.mystore.data.model.Feedback
import com.jpatrick.mystore.data.model.dto.UpdateProductToCart
import com.jpatrick.mystore.databinding.ActivityDetailBinding
import com.jpatrick.mystore.utils.LoadFormat

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var listFeedbacks: List<Feedback>
    private lateinit var adapter: CommentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish()
        }
        val factory = DetailViewModelFactory(this)
        val detailViewModel =
            ViewModelProvider(this, factory)[DetailViewModel::class.java]

        val loadFormat = LoadFormat()
        // Thiết lập RecyclerView
        binding.listFeedbacks.layoutManager = LinearLayoutManager(this)
        detailViewModel.data.observe(this) {
            binding.name.text = it.product.name
            loadFormat.loadCurrency(it.product.price, binding.salePrice)
            loadFormat.loadImage(it.store.image, binding.storeAvatar)
            binding.storeName.text = it.store.username
            binding.description.text = it.product.description
            listFeedbacks = it.feedbacks
            adapter = CommentAdapter(listFeedbacks) { feedbackId ->

            }
            val size = adapter.itemCount
            binding.productRate.text = "4.8/5 ($size đánh giá)"
//            binding.productRate.text = "${it.product.rate}/5 ($size đánh giá)"
            binding.listFeedbacks.adapter = adapter
        }

        val productId = intent.getStringExtra("PRODUCT_ID")
        if (productId != null) {
            detailViewModel.fetchData(productId, false)
            binding.btnAddCart.setOnClickListener {
                detailViewModel.updateProductToCart(UpdateProductToCart(productId, 1), this)
            }
        } else {
            Toast.makeText(this, "Lỗi không lấy đc id sản phẩm", Toast.LENGTH_SHORT).show()
        }
    }
}