package com.jpatrick.mystore.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.jpatrick.mystore.data.model.Product
import com.jpatrick.mystore.databinding.FragmentHomeBinding
import com.jpatrick.mystore.ui.detail.DetailActivity
import com.jpatrick.mystore.ui.notification.NotificationsViewModel
import com.jpatrick.mystore.ui.notification.NotificationsViewModelFactory
import java.text.Normalizer
import java.util.regex.Pattern

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private var listProduct: List<Product> = mutableListOf()
    private lateinit var adapter: HomeAdapter
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val factory = HomeViewModelFactory(requireContext())
        val homeViewModel =
            ViewModelProvider(this, factory)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        homeViewModel.checkTokenAndRefresh()

        // Thiết lập RecyclerView
        binding.recyclerViewListProduct.layoutManager = GridLayoutManager(requireActivity(), 2)

        // Quan sát LiveData từ ViewModel
        homeViewModel.data.observe(viewLifecycleOwner) {
            listProduct = it
            adapter = HomeAdapter(listProduct) { productId ->
                val intent = Intent(requireActivity(),  DetailActivity::class.java)
                intent.putExtra("PRODUCT_ID", productId)
                startActivity(intent)
            }
            binding.recyclerViewListProduct.adapter = adapter
            binding.swipeRefreshLayout.isRefreshing = false
        }

        binding.search.setOnClickListener {
            binding.search.isIconified = false
            binding.search.requestFocus()
        }

        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }
        })

        // Cấu hình pull-to-refresh
        binding.swipeRefreshLayout.setOnRefreshListener {
            // Khi kéo để refresh, gọi hàm fetchData để làm mới dữ liệu
            homeViewModel.fetchData(true)
        }

        // Gọi dữ liệu từ server khi khởi tạo fragment
        homeViewModel.fetchData(false)
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun filterList(query: String?) {
        val filtered = if (!query.isNullOrEmpty()) {
            val searchText = removeDiacritics(query.lowercase())
            listProduct.filter { item ->
                val itemName = removeDiacritics(item.name.lowercase())
                itemName.contains(searchText)
            }
        } else {
            listProduct
        }
        adapter.updateProducts(filtered)
    }

    private fun removeDiacritics(text: String): String {
        val normalized = Normalizer.normalize(text, Normalizer.Form.NFD)
        return Pattern.compile("\\p{InCombiningDiacriticalMarks}+").matcher(normalized).replaceAll("")
    }
}