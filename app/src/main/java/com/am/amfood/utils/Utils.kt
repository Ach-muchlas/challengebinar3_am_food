package com.am.amfood.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.navigation.NavController
import com.am.amfood.R
import java.text.DecimalFormat

object Utils {
    const val DETAIL_TO_HOME = "detailToHome"
    const val DETAIL_TO_CART = "detailToCart"
    const val CHECKOUT_TO_CART = "checkoutToCart"
    const val CART_TO_CHECKOUT = "cartToCheckout"
    const val HOME_TO_PROFILE = "homeToProfile"
    const val CHECKOUT_TO_HOME = "checkoutToHome"
    const val TO_MAPS = "toMaps"

    fun formatCurrency(amount: Double): String {
        val decimal = DecimalFormat("#,###.##")
        return "Rp. " + decimal.format(amount)
    }

    fun toastMessage(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun navigateToDestination(
        destination: String,
        navController: NavController,
    ) {
        navController.let {
            when (destination) {
                DETAIL_TO_HOME -> it.navigate(R.id.action_detailFragment_to_navigation_home)
                DETAIL_TO_CART -> it.navigate(R.id.action_detailFragment_to_navigation_keranjang)
                HOME_TO_PROFILE -> it.navigate(R.id.action_navigation_home_to_navigation_profile)
                CART_TO_CHECKOUT -> it.navigate(R.id.action_navigation_cart_to_checkOutFragment)
                CHECKOUT_TO_HOME -> it.navigate(R.id.action_checkOutFragment_to_navigation_home)
                else -> "Incorrect or invalid your destination"
            }
        }
    }

    fun navigateToMaps(latitude: Double?, longitude: Double?, context: Context) {
        val uri = Uri.parse("http://maps.google.com/maps?q=loc:${latitude},${longitude}")
        val mapIntent = Intent(Intent.ACTION_VIEW, uri)
        mapIntent.setPackage("com.google.android.apps.maps")
        context.startActivity(mapIntent)
    }

}
