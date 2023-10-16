package com.am.amfood.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.am.amfood.R
import com.am.amfood.data.remote.response.DataItem
import com.am.amfood.databinding.FragmentHomeBinding
import com.am.amfood.ui.auth.AuthViewModel
import com.am.amfood.ui.auth.LoginActivity
import com.am.amfood.utils.Utils
import com.am.amfood.utils.Utils.HOME_TO_PROFILE
import com.am.amfood.utils.Utils.setUpBottomNavigation
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var util: Utils
    private val viewModel: HomeViewModel by viewModels()
    private val authViewModel : AuthViewModel by viewModels()
    private lateinit var googleSignClient: GoogleSignInClient
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        setUpBottomNavigation(activity, false)
        setUpLayoutManager()
        changeLayout()
        navigateToProfile()
        setUpFirebase()

        return binding.root
    }
    private fun setUpFirebase() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignClient = GoogleSignIn.getClient(requireActivity(), gso)
        firebaseAuth = Firebase.auth

        /*Untuk LogOut*/
        /*binding.cardShop.setOnClickListener {
            authViewModel.signOut(firebaseAuth, googleSignClient)
            startActivity(Intent(requireContext(), LoginActivity::class.java))
        }*/
    }
    private fun setUpLayoutManager() {
        viewModel.isGrid.observe(viewLifecycleOwner) { isGrid ->
            viewModel.allMenu.observe(viewLifecycleOwner) { menu ->
                viewModel.setUpLayoutManager(requireContext(), binding.rvCardItem, isGrid, menu)
            }
            viewModel.setUpChangeIcon(binding.iconGridOrList, isGrid)
        }
    }

    private fun changeLayout() {
        binding.iconGridOrList.setOnClickListener {
            viewModel.changeLayout()
        }
    }

    private fun navigateToProfile() {
        binding.cardProfile.setOnClickListener {
            util.navigateToDestination(HOME_TO_PROFILE, findNavController())
        }
    }

}