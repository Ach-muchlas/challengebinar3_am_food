package com.am.amfood.ui.checkout

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.am.amfood.R
import com.am.amfood.data.remote.response.OrdersItem
import com.am.amfood.data.source.Result
import com.am.amfood.databinding.FragmentCheckOutBinding
import com.am.amfood.ui.adapter.CartAdapter
import com.am.amfood.ui.cart.CartViewModel
import com.am.amfood.utils.Utils
import com.am.amfood.utils.Utils.CHECKOUT_TO_CART
import com.am.amfood.utils.Utils.CHECKOUT_TO_HOME
import com.am.amfood.utils.Utils.navigateToDestination
import com.am.amfood.utils.Utils.setUpBottomNavigation
import com.am.amfood.utils.Utils.toastMessage
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class CheckOutFragment : Fragment() {

    private lateinit var binding: FragmentCheckOutBinding
    private val viewModel: CheckOutViewModel by viewModels()
    private val cartViewModel: CartViewModel by viewModels()
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentCheckOutBinding.inflate(inflater, container, false)

//        setUpBottomNavigation(activity, true)
//        setUpAppBar()
//        setUpDisplayCart()

        return binding.root
    }

    private fun setUpAppBar() {
        binding.appbar.textViewAppbar.text = getString(R.string.konfirmasi_pesanan)
        binding.appbar.btnBack.setOnClickListener {
            navigateToDestination(CHECKOUT_TO_CART, findNavController())
        }
    }

//    private fun setUpDisplayCart() {
//        firebaseConfiguration(requireContext())
//        firebaseAuth = Firebase.auth
//        val username = firebaseAuth.currentUser?.displayName
//
//        cartViewModel.getAllCart().observe(viewLifecycleOwner) { list ->
//            binding.rvCart.layoutManager = LinearLayoutManager(requireContext())
//            binding.rvCart.adapter = CartAdapter(list)
//
//            cartViewModel.getTotalPayment().observe(viewLifecycleOwner) { totalPayment ->
//                binding.textViewTotalPrice.text = Utils.formatCurrency(totalPayment)
//
//                val name = list[0].nameMenu
//                val price = list[0].priceMenu
//                val qty = list[0].quantityMenu
//                val note = list[0].note
//
//                val listOfOrder = OrdersItem(name, price.toInt(), qty, note)
//                binding.btnOrder.setOnClickListener {
//                    setUpCheckOutOrder(listOfOrder, totalPayment.toInt(), username ?: "Username not registered!!")
//                    cartViewModel.deleteDataCart()
//                    navigateToDestination(CHECKOUT_TO_HOME, findNavController())
//                    cartViewModel.messageToast.observe(viewLifecycleOwner) { message ->
//                        if (message.isNotEmpty()) {
//                            toastMessage(requireContext(), message)
//                        }
//                    }
//                }
//            }
//        }
//    }

    private fun firebaseConfiguration(context: Context) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(context, gso)
        firebaseAuth = Firebase.auth
    }

    private fun setUpCheckOutOrder(order: OrdersItem, total: Int, username: String) {
        viewModel.checkOutOrder(order = order, total = total, username = username)
            .observe(viewLifecycleOwner) {
                when (it) {
                    is Result.Success -> {
                        toastMessage(requireContext(), "success")
                    }

                    is Result.Error -> {
                        toastMessage(requireContext(), "eror : ${it.error}")
                    }

                    else -> {
                        toastMessage(requireContext(), "Invalid response!!")
                    }
                }

            }
    }

    override fun onDestroy() {
        super.onDestroy()
        setUpBottomNavigation(activity, false)
    }

}