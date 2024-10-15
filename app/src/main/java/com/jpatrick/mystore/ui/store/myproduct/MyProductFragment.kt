package com.jpatrick.mystore.ui.store.myproduct

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.jpatrick.mystore.R
import com.jpatrick.mystore.databinding.FragmentMyProductBinding

class MyProductFragment : Fragment() {
    private var _binding: FragmentMyProductBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MyProductViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyProductBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAddNewProduct.setOnClickListener{
            findNavController().navigate(R.id.action_myProductFragment_to_createProductFragment)
        }
    }

    @SuppressLint("ResourceAsColor")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[MyProductViewModel::class.java]
        // TODO: Use the ViewModel

        binding.btnNotAvailableProduct.setOnClickListener {
            binding.btnNotAvailableProduct.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.primary_30))
            binding.btnAvailableProduct.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.primary_10))
        }

        binding.btnAvailableProduct.setOnClickListener {
            binding.btnAvailableProduct.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.primary_30))
            binding.btnNotAvailableProduct.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.primary_10))
        }
    }

}