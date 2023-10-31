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
import com.am.amfood.R
import com.am.amfood.data.lokal.entity.Cart
import com.am.amfood.data.lokal.entity.MenuEntity
import com.am.amfood.databinding.FragmentDetailBinding
import com.am.amfood.ui.cart.CartViewModel
import com.am.amfood.utils.Utils.DETAIL_TO_CART
import com.am.amfood.utils.Utils.DETAIL_TO_HOME
import com.am.amfood.utils.Utils.navigateToDestination
import com.am.amfood.utils.Utils.setUpBottomNavigation
import com.am.amfood.utils.Utils.toastMessage
import com.bumptech.glide.Glide
import org.koin.android.ext.android.inject

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val args: DetailFragmentArgs by navArgs()
    private val viewModel: DetailViewModel by viewModels()
    private val cartViewModel: CartViewModel by inject()

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

        return view
    }

    /*This function is used to display detailed menu data.*/
    /*when the user presses one of the menus on home*/
    private fun setUpViewDetail() {
        val menu: MenuEntity = args.objectParcelable
        viewModel.setValueProduct(menu)

        viewModel.menu.observe(viewLifecycleOwner) { menuItem ->
            binding.apply {
                Glide.with(binding.root.context).load(menuItem.imageUrl).into(binding.imageViewItem)
                textViewNameItem.text = menuItem.title
                textViewValueDesc.text = menuItem.description
                textViewValuePrice.text = menuItem.priceString
                textViewValueLocation.text = menuItem.address
                Ratingbar.rating = 5.0F // this data is dummy data
                cardBack.setOnClickListener {
                    /*this function is used when user wants to return to home*/
                    navigateToDestination(DETAIL_TO_HOME, findNavController())
                }
                if (menu.isLike) {
                    likeDescription.setImageResource(R.drawable.hati)
                } else {
                    likeDescription.setImageResource(R.drawable.love)
                }
            }
        }
    }

    /*This function is only for logic when the user presses the increment or decrement button*/
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
                if (result != 0) {
                    order()
                }
            }
        }
    }

    /*This function is used to order menu items*/
    /*This function runs when the user clicks the order button*/
    private fun order() {
        binding.apply {
            btnOrder.setOnClickListener {
                addOrder()
                navigateToDestination(DETAIL_TO_CART, findNavController())
            }
        }
    }

    /*This function is used to enter menu data needed when saving data in the cart database*/
    private fun addOrder() {
        val menu: MenuEntity = args.objectParcelable
        val photo = menu.imageUrl
        val name = menu.title
        val quantity = binding.textViewQuantity.text.toString()
        val price = menu.price
        val totalAmount = price.times(quantity.toInt())

        val data = Cart(
            null,
            nameMenu = name,
            quantityMenu = quantity.toInt(),
            photoMenu = photo,
            priceMenu = price.toDouble(),
            totalAmount = totalAmount.toDouble(),
        )

        /*store data to cart database */
        cartViewModel.addDataOrUpdateCartData(data)

        /*displays a toast if successful or failed when store data to database */
        cartViewModel.messageToast.observe(viewLifecycleOwner) { message ->
            toastMessage(requireContext(), message)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        setUpBottomNavigation(activity, false)
    }
}