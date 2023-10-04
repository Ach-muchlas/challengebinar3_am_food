package com.am.amfood.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.am.amfood.R
import com.am.amfood.adapter.MenuAdapter
import com.am.amfood.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var adapter: MenuAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        viewModel.isGrid.observe(viewLifecycleOwner) { isGrid ->
            viewModel.setUpLayoutManager(requireContext(), binding.rvCardItem, isGrid)

            binding.iconGridOrList.setOnClickListener {
                viewModel.changeLayout()
            }

            viewModel.setUpChangeIcon(binding.iconGridOrList, isGrid)

            binding.cardProfile.setOnClickListener {
                viewModel.navigateToProfile(findNavController())
            }
        }



        return binding.root
    }
}