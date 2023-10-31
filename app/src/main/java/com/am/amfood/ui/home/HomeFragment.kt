package com.am.amfood.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.am.amfood.R
import com.am.amfood.data.lokal.entity.MenuEntity
import com.am.amfood.data.remote.response.CategoryResponse
import com.am.amfood.data.source.Status
import com.am.amfood.databinding.FragmentHomeBinding
import com.am.amfood.ui.adapter.CategoryAdapter
import com.am.amfood.ui.adapter.MenuAdapter
import com.am.amfood.ui.profile.ProfileViewModel
import com.am.amfood.utils.Utils.HOME_TO_CART
import com.am.amfood.utils.Utils.navigateToDestination
import com.am.amfood.utils.Utils.setUpBottomNavigation
import com.am.amfood.utils.Utils.setUpVisibilityProgressbar
import com.am.amfood.utils.Utils.toastMessage
import com.bumptech.glide.Glide
import org.koin.android.ext.android.inject

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by inject()
    private val profileViewModel: ProfileViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        setUpBottomNavigation(activity, false)
        displaysListMenu()
        displayCategoryMenu()
        navigateToProfile()
        setUpProfile()

        return binding.root
    }

    /*this function is used to display menu data*/
    private fun displaysListMenu() {
        viewModel.isGridlayout.observe(viewLifecycleOwner) { isGrid ->
            viewModel.getListMenu().observe(viewLifecycleOwner) { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        setUpVisibilityProgressbar(binding.progressBarMenu, true)
                    }

                    Status.SUCCESS -> {
                        setUpVisibilityProgressbar(binding.progressBarMenu, false)
                        setUpDataMenuAdapter(resource.data, isGrid)
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

    /*this function is used to display category menu data */
    private fun displayCategoryMenu() {
        viewModel.getCategoryMenu().observe(viewLifecycleOwner) { resource ->
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


    private fun setUpDataMenuAdapter(data: List<MenuEntity>?, isGrid: Boolean) {
        val adapter = MenuAdapter(isGrid) { menu ->
            if (menu.isLike) {
                viewModel.deleteLike(menu)
            } else {
                viewModel.saveLike(menu)
            }
        }
        setUpLayoutManager(isGrid)
        adapter.submitList(data)
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

    private fun setUpProfile() {
        profileViewModel.fetchDataCurrentUser()
        profileViewModel.getDataCurrentUser()
            .observe(viewLifecycleOwner) { user ->
                Glide.with(requireContext()).load(user.photoUrl).into(binding.cardProfile)
            }
        navigateToProfile()
    }

    private fun navigateToProfile() {
        binding.cardProfile.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_navigation_profile)
        }
    }
}