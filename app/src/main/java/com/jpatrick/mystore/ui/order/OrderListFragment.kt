package com.jpatrick.mystore.ui.order

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jpatrick.mystore.data.model.Order
import com.jpatrick.mystore.data.model.Product
import com.jpatrick.mystore.databinding.FragmentOrderListBinding
import java.util.Date

class OrderListFragment : Fragment() {

    private var _binding: FragmentOrderListBinding? = null
    private val binding get() = _binding!!
    private lateinit var orderViewModel: OrderViewModel
    private lateinit var orderList: List<Order>
    private lateinit var adapter: OrderAdapter

    companion object {
        private const val ARG_TAB_TITLE = "tab_title"

        fun newInstance(tabTitle: String) = OrderListFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_TAB_TITLE, tabTitle)
            }
        }
    }

    private lateinit var tabTitle: String
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tabTitle = arguments?.getString(ARG_TAB_TITLE) ?: ""
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOrderListBinding.inflate(inflater, container, false)
        recyclerView = binding.rvOrderList
        setupRecyclerView()
        loadData()
        return binding.root
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        // Khởi tạo adapter với danh sách rỗng hoặc dữ liệu mặc định
        adapter = OrderAdapter(requireContext(), emptyList()) { order ->
            // Xử lý sự kiện khi nhấn vào nút "Xem thêm sản phẩm"
            onMoreProductClick(order)
        }
        recyclerView.adapter = adapter
    }

    private fun loadData() {
        // Dựa trên tabTitle, bạn có thể lấy dữ liệu cho từng tab khác nhau
        when (tabTitle) {
            "Chờ xác nhận" -> {
                // Lấy dữ liệu cho Chờ xác nhận
                orderList = getOrdersForStatus("Chờ xác nhận")
            }
            "Chờ lấy hàng" -> {
                // Lấy dữ liệu cho Chờ lấy hàng
                orderList = getOrdersForStatus("Chờ lấy hàng")
            }
            "Chờ giao hàng" -> {
                // Lấy dữ liệu cho Chờ giao hàng
                orderList = getOrdersForStatus("Chờ giao hàng")
            }
            "Đánh giá" -> {
                // Lấy dữ liệu cho Đánh giá
                orderList = getOrdersForStatus("Đánh giá")
            }
        }
        // Cập nhật danh sách đơn hàng trong adapter
        adapter = OrderAdapter(requireContext(), orderList) { order ->
            onMoreProductClick(order)
        }
        recyclerView.adapter = adapter
    }

    private fun getOrdersForStatus(status: String): List<Order> {
        // Tạo danh sách đơn hàng giả để minh họa
        return listOf(
            Order(
                _id = "1",
                userId = "user_123",
                products = listOf(
                    Product(
                        _id = "1",
                        storeId = "store_123",
                        name = "Product 1",
                        image = "https://example.com/product1.jpg",
                        description = "This is the description for Product 1.",
                        price = 199.99,
                        quantity = 50,
                        sold = 20,
                        categories = arrayOf("Electronics", "Gadgets"),
                        createdAt = "2024-10-17"
                    ),
                    Product(
                        _id = "2",
                        storeId = "store_456",
                        name = "Product 2",
                        image = "https://example.com/product2.jpg",
                        description = "This is the description for Product 2.",
                        price = 299.99,
                        quantity = 30,
                        sold = 15,
                        categories = arrayOf("Home", "Furniture"),
                        createdAt = "2024-10-15"
                    ),
                    Product(
                        _id = "3",
                        storeId = "store_789",
                        name = "Product 3",
                        image = "https://example.com/product3.jpg",
                        description = "This is the description for Product 3.",
                        price = 49.99,
                        quantity = 100,
                        sold = 50,
                        categories = arrayOf("Toys", "Kids"),
                        createdAt = "2024-10-10"
                    )
                ),
                freeship = "voucher_001",
                discount = "voucher_002",
                totalAmount = 230.0,
                note = "Deliver before noon",
                status = "Processing",
                paymentStatus = "Unpaid",
                paymentMethod = "Cash",
                createdAt = Date()
            ),
            Order(
                _id = "2",
                userId = "user_456",
                products = listOf(
                    Product(
                        _id = "2",
                        storeId = "store_456",
                        name = "Product 2",
                        image = "https://example.com/product2.jpg",
                        description = "This is the description for Product 2.",
                        price = 299.99,
                        quantity = 30,
                        sold = 15,
                        categories = arrayOf("Home", "Furniture"),
                        createdAt = "2024-10-15"
                    ),
                    Product(
                        _id = "3",
                        storeId = "store_789",
                        name = "Product 3",
                        image = "https://example.com/product3.jpg",
                        description = "This is the description for Product 3.",
                        price = 49.99,
                        quantity = 100,
                        sold = 50,
                        categories = arrayOf("Toys", "Kids"),
                        createdAt = "2024-10-10"
                    )
                ),
                freeship = null,
                discount = "voucher_003",
                totalAmount = 375.0,
                note = "Leave at the front door",
                status = "Shipped",
                paymentStatus = "Paid",
                paymentMethod = "Credit Card",
                createdAt = Date()
            )
        )
    }

    private fun onMoreProductClick(order: Order) {
        // Xử lý logic khi nhấn vào nút "Xem thêm sản phẩm"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
