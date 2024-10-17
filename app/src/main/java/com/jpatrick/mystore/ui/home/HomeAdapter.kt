package com.jpatrick.mystore.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jpatrick.mystore.R
import com.jpatrick.mystore.data.model.Product
import com.jpatrick.mystore.utils.LoadFormat
import com.squareup.picasso.Picasso

class HomeAdapter(
    private var productList: List<Product>,
    private val onProductClick: (productId: String) -> Unit // Callback xử lý sự kiện click
) : RecyclerView.Adapter<HomeAdapter.ProductViewHolder>() {


    // ViewHolder là nơi ánh xạ các thành phần trong layout item_view.xml
    class ProductViewHolder(productView: View) : RecyclerView.ViewHolder(productView) {
        private val productImage: ImageView = itemView.findViewById(R.id.product_image)
        private val productName: TextView = itemView.findViewById(R.id.product_name)
        private val productPrice: TextView = itemView.findViewById(R.id.product_price)
        private val productSold: TextView = itemView.findViewById(R.id.product_sold)
        private val productRate: TextView = itemView.findViewById(R.id.product_rate)

        fun bind(product: Product, onProductClick: (productId: String) -> Unit) {
            val loadFormat = LoadFormat()
            loadFormat.loadImage(product.image, productImage)
            loadFormat.loadCurrency(product.price, productPrice)
            productName.text = product.name
            productSold.text = "Đã bán: ${product.sold}   Còn: ${product.quantity.toInt() - product.sold.toInt()}"
//            productRate.text = product.rate.toString()
            // Thiết lập sự kiện click cho item
            itemView.setOnClickListener {
                onProductClick(product._id) // Gọi hàm callback khi click vào item
            }
        }
    }

    // Tạo ViewHolder cho mỗi item của RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_product_in_home, parent, false)
        return ProductViewHolder(view)
    }

    // Gắn dữ liệu vào ViewHolder
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(productList[position], onProductClick)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    fun updateProducts(newProductList: List<Product>) {
        productList = newProductList
        notifyDataSetChanged()
    }
}
