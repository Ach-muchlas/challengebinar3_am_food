package com.am.amfood.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.am.amfood.databinding.FragmentProfileBinding
import com.am.amfood.utils.Utils.firebaseAuth
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val authFirebase: FirebaseAuth = firebaseAuth
        val dataUser = authFirebase.currentUser

        binding.apply {
            dataUser?.let {
                binding.textViewName.text = it.email
            }

        }
        return binding.root
    }

}