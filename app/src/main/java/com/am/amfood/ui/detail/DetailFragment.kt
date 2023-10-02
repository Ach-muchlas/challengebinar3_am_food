package com.am.amfood.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.am.amfood.R
import com.am.amfood.databinding.FragmentDetailBinding
import com.am.amfood.model.Product
import com.google.android.material.bottomnavigation.BottomNavigationView

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val args: DetailFragmentArgs by navArgs()
    private lateinit var viewModel: DetailViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        val view = binding.root


        val bottom = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottom?.visibility = View.GONE

        val product: Product = args.objectParcelable

        viewModel = ViewModelProvider(requireActivity())[DetailViewModel::class.java]

        viewModel.setValueProduct(product)

        viewModel.card.observe(viewLifecycleOwner) { card ->
            binding.apply {
                imageProduct.setImageResource(card.imageProduct)
                cardBack.setOnClickListener {
                    viewModel.navigateToHome(findNavController())
                }
                with(layoutContentDetail) {
                    textViewNameItem.text = card.name
                    textViewValueDesc.text = card.desc
                    textViewValuePrice.text = card.price
                    textViewValueLocation.text = card.location
                    Ratingbar.rating = card.rate.toFloat()
                    iconMaps.setOnClickListener {
                        viewModel.navigateToMaps(card.lat, card.long, requireContext())
                    }
                }
            }
        }
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        val bottom = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottom?.visibility = View.VISIBLE
    }
}