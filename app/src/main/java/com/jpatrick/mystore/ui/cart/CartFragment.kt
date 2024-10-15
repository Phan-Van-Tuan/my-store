package com.jpatrick.mystore.ui.cart

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.jpatrick.mystore.R
import com.jpatrick.mystore.data.model.dto.CartDto
import com.jpatrick.mystore.data.model.dto.UpdateProductToCart
import com.jpatrick.mystore.databinding.FragmentCartBinding
import com.jpatrick.mystore.ui.account.SignInActivity
import com.jpatrick.mystore.ui.detail.DetailActivity
import com.jpatrick.mystore.utils.LoadFormat

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private var listProduct: List<CartDto> = mutableListOf()
    private lateinit var adapter: CartAdapter
    private var totalAmount = 0.0

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val factory = CartViewModelFactory(requireContext())
        val cartViewModel = ViewModelProvider(this, factory)[CartViewModel::class.java]

        _binding = FragmentCartBinding.inflate(inflater, container, false)
        val root: View = binding.root

        cartViewModel.loginRequired.observe(viewLifecycleOwner) {
            if (it) {
                // Điều hướng người dùng đến màn hình đăng nhập
                binding.cartTitle1.text = "Bạn cần đăng nhập để dùng giỏ hàng, nhấn vào đây."
                binding.layoutPay.visibility = View.GONE
                binding.cartTitle1.setOnClickListener {
                    val intent = Intent(requireActivity(), SignInActivity::class.java)
                    startActivity(intent)
                }
            } else {
                // Đặt adapter và quản lý giỏ hàng
                cartViewModel.data.observe(viewLifecycleOwner) { carts ->
                    listProduct = carts
                    adapter = CartAdapter(
                        list = listProduct,
                        onClickProduct = { productId ->
                            val intent = Intent(requireActivity(), DetailActivity::class.java)
                            intent.putExtra("PRODUCT_ID", productId)
                            startActivity(intent)
                        },
                        editCart = { productId ->
                            Toast.makeText(
                                requireContext(),
                                "Edit Cart ID: $productId",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        deleteCart = { productId ->
                            showRemoveConfirmationDialog {
                                cartViewModel.updateProductToCart(UpdateProductToCart(productId, 0))
                            }
                        },
                        onItemCheckedChange = {
                            updateTotalAmount()
                            // Kiểm tra nếu tất cả item đều được chọn
                            binding.checkBox.isChecked = listProduct.all { item -> item.isChecked }
                        }
                    )

                    // Thiết lập RecyclerView
                    binding.listCart.layoutManager = LinearLayoutManager(requireActivity())
                    binding.listCart.adapter = adapter
                    binding.swipeRefreshLayout.isRefreshing = false

                    // Logic checkbox "Chọn tất cả"
                    binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
                        listProduct.forEach { item -> item.isChecked = isChecked }
                        adapter.notifyDataSetChanged() // Cập nhật lại danh sách sau khi chọn/bỏ chọn tất cả
                        updateTotalAmount() // Cập nhật tổng số tiền
                    }
                }

// Gọi API lấy dữ liệu giỏ hàng
                cartViewModel.fetchData(false)
            }
        }

        // Cấu hình pull-to-refresh
        binding.swipeRefreshLayout.setOnRefreshListener {
            // Khi kéo để refresh, gọi hàm fetchData để làm mới dữ liệu
            cartViewModel.fetchData(true)
        }

        cartViewModel.checkIfUserLoggedIn()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showRemoveConfirmationDialog( onConfirm: () -> Unit) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Xác nhận")
        builder.setMessage("Bạn có chắc muốn xóa sản phẩm này khỏi giỏ hàng không?")

        // Tạo dialog
        val dialog = builder.create()

        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Xóa") { _, _ ->
            onConfirm() // Nếu người dùng xác nhận xóa
        }

        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Hủy") { dialog, _ ->
            dialog.dismiss() // Đóng dialog nếu người dùng chọn Hủy
        }

        dialog.show() // Hiển thị dialog

        // Tùy chỉnh màu sắc cho nút
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(requireContext(), R.color.yellow))
    }

    private fun updateTotalAmount() {
        totalAmount = listProduct.filter { it.isChecked }.sumOf { it.product.price.toDouble() }
        LoadFormat().loadCurrency(totalAmount, binding.total)
    }
}