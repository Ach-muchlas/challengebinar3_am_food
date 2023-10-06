package com.am.amfood.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.am.amfood.R
import com.am.amfood.adapter.CartAdapter
import com.am.amfood.databinding.FragmentCartBinding
import com.am.amfood.utils.Utils.CART_TO_CHECKOUT
import com.am.amfood.utils.Utils.formatCurrency
import com.am.amfood.utils.Utils.navigateToDestination
import com.google.android.material.bottomnavigation.BottomNavigationView

class CartFragment : Fragment() {
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CartViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)


        val bottom = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottom?.visibility = View.VISIBLE

        binding.appbar.btnBack.visibility = View.GONE

        viewModel.getAllCart().observe(viewLifecycleOwner) { list ->
            val adapter = CartAdapter(list)
            binding.rvCart.layoutManager = LinearLayoutManager(requireContext())
            binding.rvCart.adapter = adapter

            var totalPayment = 0.0
            list.forEach {
                totalPayment += it.totalAmount
            }

            adapter.setOnDeleteClickListener { cartItem ->
                viewModel.deleteItem(cartItem)
            }

            adapter.setOnPlusClickListener { cart ->
                viewModel.increment(cart)

            }

            adapter.setOnMinusClickListener { cart ->
                viewModel.decrement(cart)

            }
            adapter.setAddNote { note ->
                viewModel.updateCart(note)
            }

            binding.layoutCheckOut.textViewTotalPrice.text = formatCurrency(totalPayment)
        }

        binding.layoutCheckOut.btnContentPesan.setOnClickListener {
            navigateToDestination(CART_TO_CHECKOUT, findNavController())
        }

        return binding.root
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}