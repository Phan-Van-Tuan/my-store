package com.jpatrick.mystore.ui.order

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jpatrick.mystore.R
import com.jpatrick.mystore.data.model.Order
import com.jpatrick.mystore.utils.LoadFormat

class OrderAdapter(
    val context: Context,
    private val orderList: List<Order>,
    private val onMoreClick: (Order) -> Unit
) : RecyclerView.Adapter<OrderAdapter.ProductViewHolder>() {

    // This method is used to inflate the item layout and create ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_order_list, parent, false)
        return ProductViewHolder(view)
    }

    // Bind the data with the views
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val order = orderList[position]

        holder.productName.text = order.products[0].name
        holder.productQuantity.text = order.products[0].quantity.toString()
        LoadFormat().loadCurrency(order.products[0].price, holder.productPrice)
        holder.totalMoney.text = "Tổng tiền: ${order.totalAmount}"



        // Load the product image (you can use Picasso or Glide)
        // Example with Picasso:
        // Picasso.get().load(order.imageUrl).into(holder.productImage)

        holder.btnMore.setOnClickListener {
            onMoreClick(order)
        }
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    // ViewHolder class to hold the views
    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.product_image)
        val productName: TextView = itemView.findViewById(R.id.product_name)
        val productQuantity: TextView = itemView.findViewById(R.id.product_quantity)
        val productPrice: TextView = itemView.findViewById(R.id.product_price)
        val totalMoney: TextView = itemView.findViewById(R.id.order_total_amount)
        val btnMore: TextView = itemView.findViewById(R.id.btnProductMore)
    }
}
