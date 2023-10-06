package com.am.amfood.ui.checkout

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
import com.am.amfood.databinding.FragmentCheckOutBinding
import com.am.amfood.ui.cart.CartViewModel
import com.am.amfood.utils.Utils
import com.am.amfood.utils.Utils.CHECKOUT_TO_CART
import com.am.amfood.utils.Utils.CHECKOUT_TO_HOME
import com.am.amfood.utils.Utils.navigateToDestination
import com.am.amfood.utils.Utils.toastMessage
import com.google.android.material.bottomnavigation.BottomNavigationView

class CheckOutFragment : Fragment() {

    private lateinit var binding: FragmentCheckOutBinding
    private val viewModel: CartViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCheckOutBinding.inflate(inflater, container, false)

        val bottom = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottom?.visibility = View.GONE
        binding.appbar.textViewAppbar.text = getString(R.string.konfirmasi_pesanan)

        viewModel.getAllCart().observe(viewLifecycleOwner) { list ->
            binding.rvCart.layoutManager = LinearLayoutManager(requireContext())
            binding.rvCart.adapter = CartAdapter(list)
        }

        viewModel.getTotalPayment().observe(viewLifecycleOwner) { totalPayment ->
            binding.textViewTotalPrice.text = Utils.formatCurrency(totalPayment)
        }

        binding.btnOrder.setOnClickListener {
            viewModel.deleteDataCart()
            navigateToDestination(CHECKOUT_TO_HOME, findNavController())
            viewModel.messageToast.observe(viewLifecycleOwner) { message ->
                if (message.isNotEmpty()) {
                    toastMessage(requireContext(), message)
                }
            }
        }

        binding.appbar.btnBack.setOnClickListener {
            navigateToDestination(CHECKOUT_TO_CART, findNavController())
        }

        return binding.root
    }


    override fun onDestroy() {
        super.onDestroy()
        val bottom = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottom?.visibility = View.VISIBLE
    }

}