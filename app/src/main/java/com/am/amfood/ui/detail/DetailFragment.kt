package com.am.amfood.ui.detail

import android.content.Intent
import android.net.Uri
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

        binding.apply {
            imageProduct.setImageResource(cardModel.imageProduct)
            textViewNameItem.text = cardModel.name
            textViewValueDesc.text = cardModel.desc
            textViewValuePrice.text = cardModel.price
            textViewValueLocation.text = cardModel.location
            Ratingbar.rating = cardModel.rate.toFloat()
            iconMaps.setOnClickListener {
                val uri =
                    Uri.parse("http://maps.google.com/maps?q=loc:${cardModel.lat},${cardModel.long}")
                val mapIntent = Intent(Intent.ACTION_VIEW, uri)
                mapIntent.setPackage("com.google.android.apps.maps")
                startActivity(mapIntent)
            }

            cardBack.setOnClickListener {
                it.findNavController().navigate(R.id.action_detailFragment_to_navigation_home)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        val bottom = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottom?.visibility = View.VISIBLE
    }
}