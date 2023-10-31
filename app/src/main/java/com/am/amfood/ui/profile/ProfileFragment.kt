package com.am.amfood.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.am.amfood.R
import com.am.amfood.databinding.FragmentProfileBinding
import com.am.amfood.ui.auth.AuthActivity
import com.am.amfood.ui.auth.AuthViewModel
import com.am.amfood.utils.Utils.formatNameFromEmail
import com.am.amfood.utils.Utils.intentActivityUseFinish
import com.bumptech.glide.Glide
import org.koin.android.ext.android.inject

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val authViewModel: AuthViewModel by inject()
    private val viewModel: ProfileViewModel by inject()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        setUpContentAppBar()
        getDataUser()
        signOut()

        return binding.root
    }

    private fun setUpContentAppBar() {
        binding.appbar.let {
            it.btnBack.visibility = View.GONE
            it.textViewAppbar.text = getString(R.string.profile)
        }
    }

    private fun getDataUser() {
        viewModel.fetchDataCurrentUser()
        viewModel.getDataCurrentUser().observe(viewLifecycleOwner) { user ->
            binding.apply {
                textValueUsername.text =
                    user.displayName ?: formatNameFromEmail(user.email.toString())
                textValueEmail.text = user.email
                textValuePassword.text = user.uid
                textValuePhone.text = user.phoneNumber
                Glide.with(requireContext()).load(user.photoUrl).into(binding.imageViewAvatar)
            }
        }
    }

    private fun signOut() {
        binding.textLogout.setOnClickListener {
            authViewModel.signOutUser()
            intentActivityUseFinish(requireContext(), AuthActivity::class.java)
        }
    }

    companion object {
        const val USERS = "users"
    }

}