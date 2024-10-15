package com.jpatrick.mystore.ui.store.mystore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.jpatrick.mystore.R
import com.jpatrick.mystore.databinding.FragmentMyStoreBinding


class MyStoreFragment : Fragment() {
    private var _binding: FragmentMyStoreBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyStoreBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.myProductBtn.setOnClickListener{
            findNavController().navigate(R.id.action_store_home_to_myProductFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}