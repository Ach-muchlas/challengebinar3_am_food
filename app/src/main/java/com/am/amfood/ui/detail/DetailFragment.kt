package com.am.amfood.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.am.amfood.databinding.FragmentDetailBinding
import com.am.amfood.model.Cart
import com.am.amfood.model.Product
import com.am.amfood.ui.cart.CartViewModel
import com.am.amfood.utils.Utils.DETAIL_TO_CART
import com.am.amfood.utils.Utils.DETAIL_TO_HOME
import com.am.amfood.utils.Utils.formatCurrency
import com.am.amfood.utils.Utils.navigateToDestination
import com.am.amfood.utils.Utils.navigateToMaps
import com.am.amfood.utils.Utils.setUpBottomNavigation
import com.am.amfood.utils.Utils.toastMessage

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
        val product: Product = args.objectParcelable
        viewModel.setValueProduct(product)

        viewModel.card.observe(viewLifecycleOwner) { card ->
            binding.apply {
                imageProduct.setImageResource(card.imageProduct)
                cardBack.setOnClickListener {
                    navigateToDestination(DETAIL_TO_HOME, findNavController())
                }
                with(layoutContentDetail) {
                    textViewNameItem.text = card.name
                    textViewValueDesc.text = card.desc
                    textViewValuePrice.text = formatCurrency(card.price)
                    textViewValueLocation.text = card.location
                    Ratingbar.rating = card.rate.toFloat()
                    iconMaps.setOnClickListener {
                        navigateToMaps(card.lat, card.long, requireContext())
                    }
                }
            }
        }
    }

    private fun countQuantityOrder() {
        binding.apply {
            with(layoutContentDetail) {
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
    }

    private fun order() {
        binding.apply {
            with(layoutContentDetail) {
                btnOrder.setOnClickListener {
                    addOrder()
                    navigateToDestination(DETAIL_TO_CART, findNavController())
                }
            }
        }
    }

    private fun addOrder() {
        val product: Product = args.objectParcelable
        val photo = product.photo

        val name = binding.layoutContentDetail.textViewNameItem.text.toString()
        val quantity = binding.layoutContentDetail.textViewQuantity.text.toString()
        val price = product.price

        val data = Cart(
            null,
            nameMenu = name,
            quantityMenu = quantity.toInt(),
            photoMenu = photo,
            priceMenu = price,
            totalAmount = price * quantity.toInt(),

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