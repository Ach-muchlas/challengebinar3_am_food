package com.am.amfood.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.am.amfood.R
import com.am.amfood.databinding.FragmentHomeBinding
import com.am.amfood.utils.Utils
import com.am.amfood.utils.Utils.HOME_TO_PROFILE
import com.am.amfood.utils.Utils.setUpBottomNavigation
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var util: Utils


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        setUpBottomNavigation(activity, false)
        setUpLayoutManager()
        changeLayout()
        navigateToProfile()

        return binding.root
    }

    private fun setUpLayoutManager() {
        viewModel.isGrid.observe(viewLifecycleOwner) { isGrid ->
            viewModel.setUpLayoutManager(requireContext(), binding.rvCardItem, isGrid)
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