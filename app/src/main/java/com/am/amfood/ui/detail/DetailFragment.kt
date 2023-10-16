package com.am.amfood.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.am.amfood.data.lokal.entity.Cart
import com.am.amfood.data.remote.response.DataItem
import com.am.amfood.databinding.FragmentDetailBinding
import com.am.amfood.ui.cart.CartViewModel
import com.am.amfood.utils.Utils.DETAIL_TO_CART
import com.am.amfood.utils.Utils.DETAIL_TO_HOME
import com.am.amfood.utils.Utils.navigateToDestination
import com.am.amfood.utils.Utils.setUpBottomNavigation
import com.am.amfood.utils.Utils.toastMessage
import com.bumptech.glide.Glide

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val args: DetailFragmentArgs by navArgs()
    private val viewModel: DetailViewModel by viewModels()
    private val cartViewModel: CartViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        val view = binding.root

        (activity as AppCompatActivity).supportActionBar?.hide()

        setUpBottomNavigation(activity, true)
        setUpViewDetail()
        countQuantityOrder()
        order()


        return view
    }

    private fun setUpViewDetail() {
        val menu: DataItem = args.objectParcelable
        viewModel.setValueProduct(menu)

        viewModel.menu.observe(viewLifecycleOwner) { menuItem ->
            binding.apply {
                Glide.with(binding.root.context).load(menuItem.imageUrl).into(binding.imageViewItem)
                textViewNameItem.text = menuItem.nama
                textViewValueDesc.text = menuItem.detail
                textViewValuePrice.text = menuItem.hargaFormat
                textViewValueLocation.text = menuItem.alamatResto
                Ratingbar.rating = 5.0F
//                iconMaps.setOnClickListener {
//                    navigateToMaps(card.lat, card.long, requireContext())
//                }
                cardBack.setOnClickListener {
                    navigateToDestination(DETAIL_TO_HOME, findNavController())
                }

            }
        }
    }

    private fun countQuantityOrder() {
        binding.apply {

            btnPlus.setOnClickListener {
                viewModel.incrementCountQuantity()
            }
            btnMinus.setOnClickListener {
                viewModel.decrementCountQuantity()
            }
            viewModel.counter.observe(viewLifecycleOwner) { result ->
                textViewQuantity.text = result.toString()
            }
        }
    }

    private fun order() {
        binding.apply {
            btnOrder.setOnClickListener {
                addOrder()
                navigateToDestination(DETAIL_TO_CART, findNavController())
            }
        }
    }

    private fun addOrder() {
        val menu: DataItem = args.objectParcelable
        val photo = menu.imageUrl

        val name = menu.nama
        val quantity = binding.textViewQuantity.text.toString()
        val price = menu.harga
        val totalAmount = price.times(quantity.toInt())

        Log.e(
            "SIMPLE",
            "nama : $name || quantity : $quantity ||  price : $price || total : $totalAmount"
        )

        val data = Cart(
            null,
            nameMenu = name,
            quantityMenu = quantity.toInt(),
            photoMenu = photo,
            priceMenu = price.toDouble(),
            totalAmount = totalAmount.toDouble(),
        )

        cartViewModel.addCart(data)

        cartViewModel.messageToast.observe(viewLifecycleOwner) { message ->
            if (message.isNotEmpty()) {
                toastMessage(requireContext(), message)
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        setUpBottomNavigation(activity, false)
    }
}