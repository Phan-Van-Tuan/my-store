package com.jpatrick.mystore.ui.store.myproduct

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jpatrick.mystore.R
import com.jpatrick.mystore.data.model.Product
import com.jpatrick.mystore.data.model.dto.CreateProductDto
import com.jpatrick.mystore.databinding.FragmentMyProductBinding

class MyProductFragment : Fragment() {
    private var _binding: FragmentMyProductBinding? = null
    private val binding get() = _binding!!
    private lateinit var myProductViewModel: MyProductViewModel
    private var selected: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyProductBinding.inflate(inflater, container, false)

        // Khởi tạo ViewModel
        val factory = MyProductViewModelFactory(requireContext())
        myProductViewModel = ViewModelProvider(this, factory)[MyProductViewModel::class.java]
        binding.listMyProduct.layoutManager = LinearLayoutManager(requireContext())

        myProductViewModel.data.observe(viewLifecycleOwner) {
            updateUI(selected)
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            // Khi kéo để refresh, gọi hàm fetchData để làm mới dữ liệu
            myProductViewModel.fetchData(true)
        }

        binding.btnNotAvailableProduct.setOnClickListener {
            selected = false
            updateUI(false)
        }

        binding.btnAvailableProduct.setOnClickListener {
            selected = true
            updateUI(true)
        }

        binding.btnAddNewProduct.setOnClickListener {
            findNavController().navigate(R.id.action_myProductFragment_to_createProductFragment)
        }

        // Gọi dữ liệu từ server khi khởi tạo fragment
        myProductViewModel.fetchData(false)
        updateUI(selected)
        return binding.root
    }

    private fun updateUI(statusProductList: Boolean) {
        if (statusProductList) {
            binding.btnAvailableProduct.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(), R.color.primary_30
                )
            )
            binding.btnNotAvailableProduct.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(), R.color.primary_10
                )
            )
            updateProductList(myProductViewModel.getAvailableProducts())
        } else {
            binding.btnNotAvailableProduct.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(), R.color.primary_30
                )
            )
            binding.btnAvailableProduct.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(), R.color.primary_10
                )
            )
            updateProductList(myProductViewModel.getNotAvailableProducts())
        }
    }

    private fun updateProductList(products: List<Product>) {
        val productAdapter = ProductAdapter(products, onUpdateClick = { product ->
            showUpdateDialog(product)
        }, onDeleteClick = { product ->
            showDeleteConfirmationDialog(product)
        })
        binding.listMyProduct.adapter = productAdapter
        binding.swipeRefreshLayout.isRefreshing = false
    }

    private fun showDeleteConfirmationDialog(product: Product) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Xác nhận xoá")
        builder.setMessage("Bạn có chắc chắn muốn xoá sản phẩm ${product.name} không?")

        // Nếu người dùng chọn "Xóa"
        builder.setPositiveButton("Xóa") { dialog, _ ->
            myProductViewModel.deleteProduct(product._id) // Gọi ViewModel để xoá sản phẩm
            dialog.dismiss()
        }

        // Nếu người dùng chọn "Hủy"
        builder.setNegativeButton("Hủy") { dialog, _ ->
            dialog.dismiss() // Đóng dialog
        }

        // Hiển thị dialog
        builder.create().show()
    }

    @SuppressLint("MissingInflatedId")
    private fun showUpdateDialog(product: Product) {
        val dialogView = layoutInflater.inflate(R.layout.fragment_create_product, null)
        val editTextProductName = dialogView.findViewById<EditText>(R.id.input_product_name)
        val editTextProductPrice = dialogView.findViewById<EditText>(R.id.input_product_price)
        val editTextProductQuantity = dialogView.findViewById<EditText>(R.id.input_product_quantity)
        val editTextProductDescription =
            dialogView.findViewById<EditText>(R.id.input_product_description)
        val btnUpdate = dialogView.findViewById<TextView>(R.id.create_product_btn)
        btnUpdate.visibility = View.GONE
        // Thiết lập thông tin sản phẩm vào các EditText
        editTextProductName.setText(product.name)
        editTextProductPrice.setText(product.price.toString())
        editTextProductQuantity.setText(product.quantity.toString())
        editTextProductDescription.setText(product.description)

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Cập nhật sản phẩm").setView(dialogView)
            .setPositiveButton("Cập nhật") { dialog, _ ->
                // Logic cập nhật sản phẩm
                val updatedName = editTextProductName.text.toString()
                val updatedPrice: Number? = editTextProductPrice.text.toString().toDoubleOrNull()
                val updatedDescription = editTextProductDescription.text.toString()
                val updatedQuantity: Number? =
                    editTextProductQuantity.text.toString().toDoubleOrNull()
                if (updatedPrice != null && updatedQuantity != null) {
                    val productUpdate = Product(
                        product._id,
                        product.storeId,
                        updatedName,
                        "",
                        updatedDescription,
                        updatedPrice,
                        updatedQuantity,
                        product.sold,
                        arrayOf("66e2522e0c4720ecc12aee69"),
                        product.createdAt
                    )
                    myProductViewModel.updateProduct(product._id, productUpdate)
                } else {
                    Toast.makeText(
                        requireContext(), "Giá hoặc số lượng không hợp lệ", Toast.LENGTH_SHORT
                    ).show()
                }
            }.setNegativeButton("Hủy") { dialog, _ -> dialog.dismiss() }

        val dialog = builder.create()
        dialog.setOnShowListener {
            dialog.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
            ) // Thiết lập full màn hình
        }
        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Giải phóng binding để tránh memory leak
    }
}
