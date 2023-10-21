package com.am.amfood.ui.auth.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.am.amfood.databinding.FragmentRegisterBinding
import com.am.amfood.ui.auth.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class RegisterFragment : Fragment() {
    private var _binding : FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val authViewModel : AuthViewModel by viewModels()
    private lateinit var firebaseAuth : FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        val email = binding.edtRegisEmail.text
        val password = binding.edtRegisPassword.text
        val phone = binding.edtRegisPhone.text
        firebaseAuth = Firebase.auth

        binding.btnRegis.setOnClickListener {
            authViewModel.register(
                firebaseAuth,
                email.toString(),
                password.toString(),
                phone.toString(),
                requireContext()
            )
        }
        return binding.root
    }


}