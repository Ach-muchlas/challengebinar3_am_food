package com.am.amfood.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import com.am.amfood.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.text.DecimalFormat
import java.util.Locale

object Utils {
    const val DETAIL_TO_HOME = "detailToHome"
    const val DETAIL_TO_CART = "detailToCart"
    const val CHECKOUT_TO_CART = "checkoutToCart"
    const val CART_TO_CHECKOUT = "cartToCheckout"
    const val HOME_TO_PROFILE = "homeToProfile"
    const val CHECKOUT_TO_HOME = "checkoutToHome"

    @SuppressLint("StaticFieldLeak")
    lateinit var googleSignClient: GoogleSignInClient
    lateinit var firebaseAuth: FirebaseAuth

    fun formatCurrency(amount: Double): String {
        val decimal = DecimalFormat("#,###.##")
        return "Rp. " + decimal.format(amount)
    }

    fun formatNameFromEmail(name: String): String =
        name.substringBefore("@")

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
                CHECKOUT_TO_CART -> it.navigate(R.id.action_checkOutFragment_to_navigation_cart)
                else -> "Incorrect or invalid your destination"
            }
        }
    }

    fun setUpBottomNavigation(activity: Activity?, isGone: Boolean) {
        val bottomNav = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigation)
        if (isGone) {
            bottomNav?.visibility = View.GONE
        } else {
            bottomNav?.visibility = View.VISIBLE
        }
    }

    fun firebaseConfiguration(context: Context) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignClient = GoogleSignIn.getClient(context, gso)
        firebaseAuth = Firebase.auth
    }

    fun intentActivityUseFinish(context: Context, targetActivity: Class<*>) {
        val intent = Intent(context, targetActivity)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
        if (context is AppCompatActivity) {
            context.finish()
        }
    }
}
