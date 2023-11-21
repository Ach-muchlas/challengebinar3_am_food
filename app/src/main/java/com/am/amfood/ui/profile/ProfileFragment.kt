package com.am.amfood.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
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
        fetchDataUser()
        signOut()
        editProfile()

        return binding.root
    }

    private fun setUpContentAppBar() {
        binding.appbar.let {
            it.btnBack.visibility = View.GONE
            it.textViewAppbar.text = getString(R.string.profile)
            it.btnEdit.setImageResource(R.drawable.edit)
        }
    }

    private fun fetchDataUser() {
        viewModel.fetchDataUserWithDatabase()
        viewModel.userData.observe(viewLifecycleOwner) { dataUser ->
            binding.textValueUsername.text =
                dataUser?.displayName ?: formatNameFromEmail(dataUser?.email.toString())
            binding.textValueEmail.text = dataUser?.email
            binding.textValuePassword.text = dataUser?.uid
            binding.textValuePhone.text = dataUser?.phoneNumber ?: "08786579080"
            Glide.with(requireContext()).load(dataUser?.photoUrl ?: R.drawable.profile)
                .into(binding.imageViewAvatar)
        }
    }

    private fun signOut() {
        binding.textLogout.setOnClickListener {
            authViewModel.signOutUser()
            intentActivityUseFinish(requireContext(), AuthActivity::class.java)
        }
    }

    private fun editProfile() {
        binding.appbar.btnEdit.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_profile_to_updateProfileFragment)

        }
    }

    companion object {
        const val USERS = "users"
    }

}