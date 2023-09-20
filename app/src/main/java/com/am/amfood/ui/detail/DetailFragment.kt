package com.am.amfood.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.am.amfood.R
import com.am.amfood.databinding.FragmentDetailBinding
import com.am.amfood.model.CardModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val args: DetailFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        val bottom = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottom?.visibility = View.GONE

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cardModel: CardModel = args.objectParcelable

        binding.imageProduct.setImageResource(cardModel.imageProduct)
        binding.textViewNameItem.text = cardModel.name
        binding.textViewValueDesc.text = cardModel.desc
        binding.textViewValuePrice.text = cardModel.price
        binding.textViewValueLocation.text = cardModel.location

        val rating = cardModel.rate.toFloat()

        if (rating != null) {
            binding.Ratingbar.rating = rating
        } else {
            binding.Ratingbar.rating = 0f
        }

        binding.cardBack.setOnClickListener {
            it.findNavController().navigate(R.id.action_detailFragment_to_navigation_home)
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        val bottom = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottom?.visibility = View.VISIBLE
    }

}