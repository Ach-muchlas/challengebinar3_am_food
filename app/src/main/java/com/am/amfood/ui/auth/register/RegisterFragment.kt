package com.am.amfood.ui.auth.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.am.amfood.databinding.FragmentRegisterBinding
import com.am.amfood.ui.auth.AuthViewModel
import org.koin.android.ext.android.inject


class RegisterFragment : Fragment() {
    private var _binding : FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val authViewModel : AuthViewModel by inject()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        setUpDataRegisterUser()
        return binding.root
    }

    private fun setUpDataRegisterUser() {
        val email = binding.edtRegisEmail.text
        val password = binding.edtRegisPassword.text
        val phone = binding.edtRegisPhone.text

        binding.btnRegis.setOnClickListener {
            authViewModel.registerUserWithEmailPassword(
                email.toString(),
                password.toString(),
                phone.toString(),
            )

        }
    }


}