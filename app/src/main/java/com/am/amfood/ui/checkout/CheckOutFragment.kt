package com.am.amfood.ui.checkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.am.amfood.R
import com.am.amfood.data.lokal.entity.Cart
import com.am.amfood.data.remote.response.OrdersItem
import com.am.amfood.data.source.Status
import com.am.amfood.databinding.FragmentCheckOutBinding
import com.am.amfood.ui.adapter.CartAdapter
import com.am.amfood.ui.cart.CartViewModel
import com.am.amfood.ui.profile.ProfileViewModel
import com.am.amfood.utils.Utils.CHECKOUT_TO_CART
import com.am.amfood.utils.Utils.CHECKOUT_TO_HOME
import com.am.amfood.utils.Utils.formatCurrency
import com.am.amfood.utils.Utils.navigateToDestination
import com.am.amfood.utils.Utils.setUpBottomNavigation
import com.am.amfood.utils.Utils.toastMessage
import org.koin.android.ext.android.inject

class CheckOutFragment : Fragment() {

    private lateinit var binding: FragmentCheckOutBinding
    private val viewModel: CheckOutViewModel by inject()
    private val cartViewModel: CartViewModel by inject()
    private val profileViewModel: ProfileViewModel by inject()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentCheckOutBinding.inflate(inflater, container, false)

        setUpBottomNavigation(activity, true)
        setUpAppBar()
        setUpDisplayCart()

        return binding.root
    }

    private fun setUpAppBar() {
        binding.appbar.btnEdit.visibility = View.GONE
        binding.appbar.textViewAppbar.text = getString(R.string.konfirmasi_pesanan)
        binding.appbar.btnBack.setOnClickListener {
            navigateToDestination(CHECKOUT_TO_CART, findNavController())
        }
    }

    private fun setUpDisplayCart() {
        cartViewModel.getAllCartaData().observe(viewLifecycleOwner) { list ->
            setUpDataCartAdapter(list)
        }
    }

    private fun setUpDataCartAdapter(listData: List<Cart>) {
        val dataAdapter = CartAdapter(listData)
        binding.recyclerViewCartCheckOut.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = dataAdapter
        }
        cartViewModel.getTotalPaymentData().observe(viewLifecycleOwner) { totalPayment ->
            binding.textViewTotalPrice.text = formatCurrency(totalPayment)
            val listChartItem = listData.firstOrNull()
            listChartItem?.let { item ->
                val name = item.nameMenu
                val price = item.priceMenu
                val qty = item.quantityMenu
                val note = item.note

                val order = OrdersItem(name, price.toInt(), qty, note)
                binding.btnOrder.setOnClickListener {
                    setUpCheckOutOrder(order, totalPayment.toInt())
                    cartViewModel.deleteDataCart()
                    toastMessage(requireContext(), "success")
                    navigateToDestination(CHECKOUT_TO_HOME, findNavController())
                    cartViewModel.messageToast.observe(viewLifecycleOwner) { message ->
                        if (message.isNotEmpty()) {
                            toastMessage(requireContext(), message.toString())
                        }
                    }
                }
            }
        }
    }


    private fun setUpCheckOutOrder(order: OrdersItem, total: Int) {
        profileViewModel.fetchDataUserWithDatabase()
        profileViewModel.userData.observe(viewLifecycleOwner) { user ->
            viewModel.checkOutOrder(
                order = order,
                total = total,
                username = user.username.toString()
            )
                .observe(viewLifecycleOwner) { resources ->
                    when (resources.status) {
                        Status.LOADING -> {
                            toastMessage(requireContext(), "Wait....")
                        }

                        Status.SUCCESS -> {
                            toastMessage(requireContext(), "success")
                        }

                        Status.ERROR -> {
                            toastMessage(requireContext(), "error : ${resources.message}")
                        }
                    }
                }
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        setUpBottomNavigation(activity, false)
    }

}