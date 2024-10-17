package com.jpatrick.mystore.ui.store.myproduct

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.jpatrick.mystore.R
import com.jpatrick.mystore.data.model.dto.CreateProductDto
import com.jpatrick.mystore.databinding.FragmentCreateProductBinding

class CreateProductFragment : Fragment() {
    private var _binding: FragmentCreateProductBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateProductBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val factory = MyProductViewModelFactory(requireContext())
        val createProductViewModel =
            ViewModelProvider(this, factory)[MyProductViewModel::class.java]

        // Gọi hàm createProduc
        binding.createProductBtn.setOnClickListener {
            val name = binding.inputProductName.text.toString().trim() // Lấy tên sản phẩm
            val description =
                binding.inputProductDescription.text.toString().trim() // Lấy mô tả sản phẩm
            val price: Number? = binding.inputProductPrice.text.toString().trim()
                .toDoubleOrNull() // Lấy giá và chuyển đổi
            val quantity: Number? = binding.inputProductQuantity.text.toString().trim()
                .toDoubleOrNull() // Lấy số lượng và chuyển đổi

            // Kiểm tra xem cả giá và số lượng có hợp lệ không
            if (price != null && quantity != null) {
                val product = CreateProductDto(
                    "",
                    name,
                    description,
                    price,
                    quantity,
                    arrayOf("66e2522e0c4720ecc12aee69")
                )
                createProductViewModel.createProduct(product) { result ->
                    result.onSuccess {
                        findNavController().navigate(R.id.action_createProductFragment_to_myProductFragment)
                    }.onFailure { e ->
                        Toast.makeText(context, "Lỗi: $e", Toast.LENGTH_SHORT).show()
                    }
                }


            } else {
                // Hiển thị thông báo lỗi nếu giá hoặc số lượng không hợp lệ
                Toast.makeText(
                    requireContext(),
                    "Vui lòng nhập giá và số lượng hợp lệ.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        return root
    }
}