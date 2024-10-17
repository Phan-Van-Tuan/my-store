package com.jpatrick.mystore.ui.order

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.jpatrick.mystore.R
import com.jpatrick.mystore.databinding.FragmentCreateOrderBinding
import com.jpatrick.mystore.utils.LoadFormat

class CreateOrderFragment : Fragment() {
    companion object {
        fun newInstance() = CreateOrderFragment()
    }

    private var _binding: FragmentCreateOrderBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CreateOrderViewModel
    private lateinit var adapter: CreateOderAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateOrderBinding.inflate(inflater, container, false)
        binding.listProduct.layoutManager = LinearLayoutManager(requireActivity())
        viewModel = ViewModelProvider(requireActivity())[CreateOrderViewModel::class.java]
        viewModel.data.observe(viewLifecycleOwner) { list ->
            adapter = CreateOderAdapter(list)
            binding.listProduct.adapter = adapter

            val totalAmount = list.sumOf { it.product.price.toDouble() }
            LoadFormat().loadCurrency(totalAmount, binding.moneyProductsTotal)

            LoadFormat().loadCurrency(30000.0, binding.moneyShippingTotal)

            LoadFormat().loadCurrency(totalAmount + 30000, binding.moneyPaymentTotal)

            LoadFormat().loadCurrency(totalAmount + 30000, binding.total)
        }

        binding.pay.setOnClickListener {
            
        }



        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}