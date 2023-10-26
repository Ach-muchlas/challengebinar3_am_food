package com.am.amfood.ui.like

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.am.amfood.databinding.FragmentLikeBinding
import com.am.amfood.ui.adapter.MenuAdapter
import com.am.amfood.ui.viewmodel.HomeViewModel
import com.am.amfood.ui.viewmodel.ViewModelFactory

class LikeFragment : Fragment() {
    private var _binding: FragmentLikeBinding? = null
    private val binding get() = _binding!!
    private lateinit var factory: ViewModelFactory
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLikeBinding.inflate(layoutInflater, container, false)

        factory = ViewModelFactory.getInstance(requireActivity())
        homeViewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]

        val adapter = MenuAdapter(false) { menu ->
            if (menu.isLike) {
                homeViewModel.deleteLike(menu)
            } else {
                homeViewModel.saveLike(menu)
            }
        }

        homeViewModel.getLikeMenu().observe(viewLifecycleOwner){likeMenu ->
            adapter.submitList(likeMenu)
            binding.rvMenuLike.adapter = adapter
            binding.rvMenuLike.layoutManager = LinearLayoutManager(requireContext())
        }

        return binding.root
    }

}