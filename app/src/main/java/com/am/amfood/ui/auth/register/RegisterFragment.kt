package com.am.amfood.ui.auth.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.am.amfood.R
import com.am.amfood.databinding.FragmentRegisterBinding
import com.am.amfood.ui.auth.AuthViewModel
import com.am.amfood.utils.Utils
import com.am.amfood.utils.Utils.firebaseAuth


class RegisterFragment : Fragment() {
    private var _binding : FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val authViewModel : AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        val email = binding.edtRegisEmail.text
        val password = binding.edtRegisPassword.text
        val firebaseAuth = firebaseAuth

        binding.btnRegis.setOnClickListener {
            authViewModel.register(firebaseAuth, email.toString(), password.toString(), requireContext())
        }
        return binding.root
    }


}