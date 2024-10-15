package com.jpatrick.mystore.ui.personal

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.jpatrick.mystore.StoreActivity
import com.jpatrick.mystore.databinding.FragmentPersonalBinding
import com.jpatrick.mystore.ui.account.SignInActivity
import com.jpatrick.mystore.utils.LoadFormat

class PersonalFragment : Fragment() {
    private var _binding: FragmentPersonalBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val factory = PersonalViewModelFactory(requireContext())
        val personalViewModel =
            ViewModelProvider(this, factory)[PersonalViewModel::class.java]

        _binding = FragmentPersonalBinding.inflate(inflater, container, false)
        val root: View = binding.root

        personalViewModel.loginRequired.observe(viewLifecycleOwner) {
            if (it) {
                // Điều hướng người dùng đến màn hình đăng nhập
                binding.personalTitle.visibility = View.VISIBLE
                binding.layoutMain.visibility = View.GONE
                binding.personalTitle.setOnClickListener{
                    val intent = Intent(requireActivity(), SignInActivity::class.java)
                    startActivity(intent)
                }
            } else {
                personalViewModel.data.observe(viewLifecycleOwner) { result ->
                    result.onSuccess { user ->
                        binding.personalName.text = user.username
                        binding.personalRole.text = user.role
                        val loadFormat = LoadFormat()
                        loadFormat.loadImage(user.image, binding.personalAvatar)
                    }
                }
            }

            }

        personalViewModel.checkIfUserLoggedIn()
        personalViewModel.fetchData()

        binding.personalLogout.setOnClickListener{
            personalViewModel.logout()
        }

        binding.personalRole.setOnClickListener{
            val intent = Intent(requireActivity(), StoreActivity::class.java)
            startActivity(intent)
            ActivityCompat.finishAffinity(requireActivity())
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}