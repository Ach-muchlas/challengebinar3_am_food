package com.am.amfood.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.am.amfood.databinding.FragmentCartBinding
import com.am.amfood.ui.adapter.CartAdapter
import com.am.amfood.utils.Utils.CART_TO_CHECKOUT
import com.am.amfood.utils.Utils.formatCurrency
import com.am.amfood.utils.Utils.navigateToDestination
import com.am.amfood.utils.Utils.setUpBottomNavigation
import org.koin.android.ext.android.inject

class CartFragment : Fragment() {
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CartViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)

        setUpAppBar()
        setUpCart()
        setUpBottomNavigation(activity, false)
        getTotalPayment()


        return binding.root
    }

    private fun setUpAppBar() {
        binding.appbar.apply {
            btnBack.visibility = View.GONE
            btnEdit.visibility = View.GONE
        }
    }

    private fun orderItem() {
        binding.layoutCheckOut.btnContentPesan.setOnClickListener {
            navigateToDestination(CART_TO_CHECKOUT, findNavController())
        }
    }

    private fun setUpCart() {
        viewModel.getAllCartaData().observe(viewLifecycleOwner) { listDataCart ->
            val adapter = CartAdapter(listDataCart)
            binding.recyclerViewCart.layoutManager = LinearLayoutManager(requireContext())
            binding.recyclerViewCart.adapter = adapter

            adapter.setOnDeleteClickListener { cartItem ->
                viewModel.deleteItem(cartItem)
            }

            adapter.setOnPlusClickListener { cartItem ->
                viewModel.increment(cartItem)

            }

            adapter.setOnMinusClickListener { cartItem ->
                viewModel.decrement(cartItem)
            }

            adapter.setAddNote { cartNote ->
                viewModel.updateCart(cartNote)
            }


            if (listDataCart.isEmpty()) {
                binding.textEmptyCart.visibility = View.VISIBLE
            } else {
                binding.textEmptyCart.visibility = View.GONE
                orderItem()
            }
        }
    }

    private fun getTotalPayment() {
        viewModel.getTotalPaymentData().observe(viewLifecycleOwner) { totalPayment ->
            binding.layoutCheckOut.textViewContentValueTotalPrice.text =
                formatCurrency(totalPayment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}