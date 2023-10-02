package com.am.amfood.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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
                viewModel.toggleView()
            }

            viewModel.setUpChangeIcon(binding.iconGridOrList, isGrid)
        }

        return binding.root
    }
}