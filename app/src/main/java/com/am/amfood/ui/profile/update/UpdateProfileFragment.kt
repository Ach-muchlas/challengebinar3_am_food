package com.am.amfood.ui.profile.update

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.am.amfood.R
import com.am.amfood.databinding.FragmentUpdateProfileBinding
import com.am.amfood.ui.profile.ProfileViewModel
import com.am.amfood.utils.Utils.setUpBottomNavigation
import com.am.amfood.utils.Utils.toastMessage
import org.koin.android.ext.android.inject

class UpdateProfileFragment : Fragment() {
    private var _binding: FragmentUpdateProfileBinding? = null
    private val binding get() = _binding!!
    private val profileViewModel: ProfileViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateProfileBinding.inflate(inflater, container, false)

        setUpBottomNavigation(activity, true)
//        setUpVerification()

        return binding.root
    }

//    private fun setUpVerification(){
//        profileViewModel.checkEmailVerificationStatus()
//        profileViewModel.isEmailVerified.observe(viewLifecycleOwner){isVerified ->
//            if (isVerified){
//                binding.textview.te = "Email has been verified"
//            }else{
//                binding.textview.text ="Email has not verified"
//            }
//
//            binding.button.setOnClickListener {
//                profileViewModel.sendEmailVerification(requireContext())
//            }
//
//
//        }
//
//    }

//    private fun setUpUpdateData() {
//        val username = binding.edtUpdateUserName.text.toString()
//        val email = binding.edtUpdateEmail.text.toString()
//        val password = binding.edtUpdatePassword.text.toString()
//
//        profileViewModel.updateProfileUser(username, email, password, {
//            findNavController().navigate(R.id.action_navigation_profile_to_updateProfileFragment)
//        }, {
//            toastMessage(requireContext(), it)
//            Log.e("SIMPLEUPDATE", it)
//        })
//    }

}