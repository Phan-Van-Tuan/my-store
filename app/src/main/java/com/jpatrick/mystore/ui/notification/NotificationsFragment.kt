package com.jpatrick.mystore.ui.notification

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jpatrick.mystore.databinding.FragmentNotificationsBinding
import com.jpatrick.mystore.ui.account.SignInActivity

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private lateinit var list: List<String>
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val factory = NotificationsViewModelFactory(requireContext())
        val viewModel = ViewModelProvider(this, factory)[NotificationsViewModel::class.java]

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel.loginRequired.observe(viewLifecycleOwner) {
            if (it) {
                // Điều hướng người dùng đến màn hình đăng nhập
                binding.notificationTitle1.text = "Bạn cần đăng nhập để xem thông báo, nhấn vào đây."
                binding.notificationTitle1.setOnClickListener{
                    val intent = Intent(requireActivity(), SignInActivity::class.java)
                    startActivity(intent)
                }
            } else {
                // Người dùng đã đăng nhập, tiếp tục với logic giỏ hàng
//                NotificationsViewModel.data.observe(viewLifecycleOwner) {
//                    list = it
//                    adapter = NotificationAdapter(
//                        list = list,
//                        onClickProduct = { productId ->
//                            val intent = Intent(requireActivity(),  DetailProduct::class.java)
//                            startActivity(intent)
//                        },
//                        editCart = { cartId ->
//                            // Handle edit cart
//                            Toast.makeText(requireContext(), "Edit Cart ID: $cartId", Toast.LENGTH_SHORT).show()
//                        },
//                        deleteCart = { cartId ->
//                            // Handle delete cart
//                            Toast.makeText(requireContext(), "Delete Cart ID: $cartId", Toast.LENGTH_SHORT).show()
//                        })
//                    binding.listNotification.layoutManager = LinearLayoutManager(requireActivity())
//                    binding.swipeRefreshLayout.isRefreshing = false
//                    binding.listNotification.adapter = adapter
//                }
//                NotificationsViewModel.fetchData(false)
            }
        }

        // Cấu hình pull-to-refresh
        binding.swipeRefreshLayout.setOnRefreshListener {
            // Khi kéo để refresh, gọi hàm fetchData để làm mới dữ liệu
            viewModel.fetchData(true)
        }

        viewModel.checkIfUserLoggedIn()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}