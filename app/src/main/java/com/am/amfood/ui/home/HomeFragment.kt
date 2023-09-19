package com.am.amfood.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.am.amfood.R
import com.am.amfood.adapter.ListCardAdapter
import com.am.amfood.databinding.FragmentHomeBinding
import com.am.amfood.model.dummyDataCard

class HomeFragment : Fragment() {
    private var _binding : FragmentHomeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ListCardAdapter(requireActivity(), dummyDataCard)

        binding.apply {
            rvCardItem.layoutManager = GridLayoutManager(requireActivity(), 2)
            rvCardItem.adapter = adapter
        }
    }


}