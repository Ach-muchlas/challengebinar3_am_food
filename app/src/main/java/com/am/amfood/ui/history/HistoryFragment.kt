package com.am.amfood.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.am.amfood.adapter.MenuAdapter
import com.am.amfood.databinding.FragmentHistoryBinding
import com.am.amfood.model.dummyDataCard

class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = MenuAdapter(dummyDataCard)
        binding.apply {
            rvMenuHistory.hasFixedSize()
            adapter.isLinear = true
            rvMenuHistory.layoutManager = LinearLayoutManager(requireContext())
            rvMenuHistory.adapter = adapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}