package com.jpatrick.mystore.ui.order

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jpatrick.mystore.R
import com.jpatrick.mystore.data.model.dto.CartDto
import com.jpatrick.mystore.utils.LoadFormat

class CreateOderAdapter(
    private var list: List<CartDto>,
) : RecyclerView.Adapter<CreateOderAdapter.ItemViewHolder>() {

    class ItemViewHolder(productView: View) : RecyclerView.ViewHolder(productView) {
        private val productImage = itemView.findViewById<ImageView>(R.id.product_image)
        private val productName = itemView.findViewById<TextView>(R.id.product_name)
        private val productPrice = itemView.findViewById<TextView>(R.id.product_price)
        private val productQuantity = itemView.findViewById<TextView>(R.id.product_quantity)

        fun bind(
            cart: CartDto
        ) {
            productName.text = cart.product.name
            productQuantity.text = cart.quantity.toString()
            val loadFormat = LoadFormat()
            loadFormat.loadImage(cart.product.image, productImage)
            loadFormat.loadCurrency(cart.product.price, productPrice)

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_product_in_create_order, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}