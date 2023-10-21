package com.am.amfood.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.am.amfood.data.remote.response.DataItemCategory
import com.am.amfood.data.source.Result
import com.am.amfood.databinding.FragmentHomeBinding
import com.am.amfood.ui.adapter.CategoryAdapter
import com.am.amfood.utils.Utils
import com.am.amfood.utils.Utils.HOME_TO_CART
import com.am.amfood.utils.Utils.HOME_TO_PROFILE
import com.am.amfood.utils.Utils.navigateToDestination
import com.am.amfood.utils.Utils.setUpBottomNavigation
import com.am.amfood.utils.Utils.toastMessage
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var util: Utils
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var firebaseAuth : FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        setUpBottomNavigation(activity, false)
        setUpDisplayCategoryMenu()
        setUpDisplayMenu()
        changeLayout()
        navigateToProfile()
        binding.cardShop.setOnClickListener {
            navigateToDestination(HOME_TO_CART, findNavController())
        }

        return binding.root
    }

    private fun setUpDisplayCategoryMenu() {
        viewModel.getCategoryMenu().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Success -> {
                    setUpDataCategory(result.data)
                }

                is Result.Error -> {
                    toastMessage(requireContext(), result.error)
                }

                else -> {
                    toastMessage(requireContext(), "Unknown Response")
                }

            }
        }
    }

    private fun setUpDataCategory(data: List<DataItemCategory>) {
        val adapter = CategoryAdapter()
        adapter.submitList(data)
        binding.recyclerViewCategory.adapter = adapter
        binding.recyclerViewCategory.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setUpDisplayMenu() {
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
        firebaseAuth = Firebase.auth
        val photoUrl = firebaseAuth.currentUser?.photoUrl
        Glide.with(requireContext()).load(photoUrl).into(binding.cardProfile)
        binding.cardProfile.setOnClickListener {
            util.navigateToDestination(HOME_TO_PROFILE, findNavController())
        }
    }
}