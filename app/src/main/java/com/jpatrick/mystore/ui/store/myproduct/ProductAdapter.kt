package com.jpatrick.mystore.ui.store.myproduct

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jpatrick.mystore.R
import com.jpatrick.mystore.data.model.Product
import com.jpatrick.mystore.utils.LoadFormat

class ProductAdapter(
    private val productList: List<Product>,
    private val onUpdateClick: (Product) -> Unit,
    private val onDeleteClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.product_image)
        val productName: TextView = itemView.findViewById(R.id.product_name)
        val productPrice: TextView = itemView.findViewById(R.id.product_price)
        val productQuantity: TextView = itemView.findViewById(R.id.product_quantity)
        val btnUpdate: TextView = itemView.findViewById(R.id.btn_update)
        val btnDelete: TextView = itemView.findViewById(R.id.btn_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_product_in_store, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]

        holder.productName.text = product.name
        holder.productQuantity.text = "Đã bán: ${product.sold}   Còn: ${product.quantity.toInt() - product.sold.toInt()}"
        val loadFormat = LoadFormat()
        loadFormat.loadImage(product.image, holder.productImage)
        loadFormat.loadCurrency(product.price, holder.productPrice)

        // Handle update button click
        holder.btnUpdate.setOnClickListener {
            onUpdateClick(product)
        }

        // Handle delete button click
        holder.btnDelete.setOnClickListener {
            onDeleteClick(product)
        }
    }

    override fun getItemCount(): Int = productList.size
}
