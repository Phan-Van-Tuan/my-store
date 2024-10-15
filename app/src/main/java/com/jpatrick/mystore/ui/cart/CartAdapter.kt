package com.jpatrick.mystore.ui.cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jpatrick.mystore.R
import com.jpatrick.mystore.data.model.Product
import com.jpatrick.mystore.data.model.dto.CartDto
import com.jpatrick.mystore.utils.LoadFormat
import com.squareup.picasso.Picasso

class CartAdapter(
    private var list: List<CartDto>,
    private val onClickProduct: (productId: String) -> Unit,
    private val editCart: (productId: String) -> Unit,
    private val deleteCart: (productId: String) -> Unit,
    private val onItemCheckedChange: (Boolean) -> Unit,
) : RecyclerView.Adapter<CartAdapter.ItemViewHolder>() {

    class ItemViewHolder(productView: View) : RecyclerView.ViewHolder(productView) {
        private val checkBox = itemView.findViewById<CheckBox>(R.id.product_checkbox)
        private val productImage = itemView.findViewById<ImageView>(R.id.product_image)
        private val productName = itemView.findViewById<TextView>(R.id.product_name)
        private val productPrice = itemView.findViewById<TextView>(R.id.product_price)
        private val productQuantity = itemView.findViewById<TextView>(R.id.product_quantity)
        private val btnUpdate = itemView.findViewById<TextView>(R.id.btn_edit_cart)
        private val btnDelete = itemView.findViewById<TextView>(R.id.btn_remove_product)

        fun bind(
            cart: CartDto,
            onProductClick: (productId: String) -> Unit,
            onCartUpdate: (productId: String) -> Unit,
            onCartDelete: (productId: String) -> Unit,
            onItemCheckedChange: (Boolean) -> Unit,
        ) {
            productName.text = cart.product.name
            productQuantity.text = cart.quantity.toString()
            val loadFormat = LoadFormat()
            loadFormat.loadImage(cart.product.image, productImage)
            loadFormat.loadCurrency(cart.product.price, productPrice)

            // Đặt lại trạng thái của checkbox trước khi thiết lập listener
            checkBox.setOnCheckedChangeListener(null)
            checkBox.isChecked = cart.isChecked

            // Thiết lập các listener sau khi đặt lại checkbox
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                cart.isChecked = isChecked
                onItemCheckedChange(isChecked) // Gọi callback khi checkbox thay đổi
            }

            itemView.setOnClickListener {
                onProductClick(cart.product._id)
            }

            btnUpdate.setOnClickListener {
                // edit logic
                onCartUpdate(cart.product._id)
            }

            btnDelete.setOnClickListener {
                // delete logic
                onCartDelete(cart.product._id)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_product_in_cart, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(list[position], onClickProduct, editCart, deleteCart, onItemCheckedChange)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    // Hàm để xóa item khỏi danh sách và cập nhật adapter
    private fun removeItem(position: Int) {
//        list.removeAt(position)
//        notifyItemRemoved(position)
//        notifyItemRangeChanged(position, itemList.size)
    }
}
