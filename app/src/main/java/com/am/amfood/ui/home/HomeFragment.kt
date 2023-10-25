package com.am.amfood.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.am.amfood.R
import com.am.amfood.data.remote.response.CategoryResponse
import com.am.amfood.data.remote.response.MenuResponse
import com.am.amfood.data.source.Status
import com.am.amfood.databinding.FragmentHomeBinding
import com.am.amfood.ui.adapter.CategoryAdapter
import com.am.amfood.ui.adapter.MenuAdapter
import com.am.amfood.ui.viewmodel.HomeViewModel
import com.am.amfood.ui.viewmodel.ViewModelFactory
import com.am.amfood.utils.Utils
import com.am.amfood.utils.Utils.HOME_TO_CART
import com.am.amfood.utils.Utils.HOME_TO_PROFILE
import com.am.amfood.utils.Utils.navigateToDestination
import com.am.amfood.utils.Utils.setUpBottomNavigation
import com.am.amfood.utils.Utils.setUpVisibilityProgressbar
import com.am.amfood.utils.Utils.toastMessage
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var util: Utils
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var factory: ViewModelFactory
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        setUpBottomNavigation(activity, false)
        factory = ViewModelFactory.getInstance(requireActivity())
        viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]

        displaysListMenu()
        displayCategoryMenu()
        navigateToProfile()
        navigateToShop()

        return binding.root
    }

    private fun displaysListMenu() {
        viewModel.isGridlayout.observe(viewLifecycleOwner) { isGrid ->
            viewModel.getListMenu().observe(viewLifecycleOwner) { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        setUpVisibilityProgressbar(binding.progressBarMenu, true)
                    }

                    Status.SUCCESS -> {
                        Log.e("SIMPLE", "SUCCESS")
                        setUpDataMenuAdapter(data = resource.data, isGrid)
                        setUpLayoutManager(isGrid)
                        setUpVisibilityProgressbar(binding.progressBarMenu, false)
                        changeLayout()
                    }

                    Status.ERROR -> {
                        Log.e("SIMPLE", "onFailure : ${resource.message}")
                        setUpVisibilityProgressbar(binding.progressBarMenu, false)
                        toastMessage(requireContext(), resource.message.toString())
                    }
                }

            }
        }
    }

    private fun displayCategoryMenu() {
        viewModel.getListCategoryMenu().observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.LOADING -> {
                    setUpVisibilityProgressbar(binding.progressBarCategoryMenu, true)
                }

                Status.SUCCESS -> {
                    setUpDataCategoryAdapter(resource.data)
                    setUpVisibilityProgressbar(binding.progressBarCategoryMenu, false)
                }

                Status.ERROR -> {
                    toastMessage(requireContext(), "Error : ${resource.message}")
                    setUpVisibilityProgressbar(binding.progressBarCategoryMenu, false)
                }
            }
        }
    }


    private fun setUpDataMenuAdapter(data: MenuResponse?, isGrid: Boolean) {
        val adapter = MenuAdapter(isGrid)
        adapter.submitList(data?.data)
        binding.recyclerViewMenu.adapter = adapter
    }

    private fun setUpDataCategoryAdapter(data: CategoryResponse?) {
        val adapter = CategoryAdapter()
        adapter.submitList(data?.data)
        binding.recyclerViewCategory.adapter = adapter
        binding.recyclerViewCategory.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setUpLayoutManager(isGrid: Boolean) {
        val iconGrid = R.drawable.more
        val iconList = R.drawable.list
        binding.iconGridOrList.setImageResource(if (isGrid) iconGrid else iconList)
        val layoutManager = if (isGrid) {
            GridLayoutManager(context, 2)
        } else {
            LinearLayoutManager(context)
        }
        binding.recyclerViewMenu.layoutManager = layoutManager
    }

    private fun changeLayout() {
        binding.iconGridOrList.setOnClickListener {
            viewModel.setUpIsGridLayout()
        }
    }

    private fun navigateToShop() {
        binding.cardShop.setOnClickListener {
            navigateToDestination(HOME_TO_CART, findNavController())
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