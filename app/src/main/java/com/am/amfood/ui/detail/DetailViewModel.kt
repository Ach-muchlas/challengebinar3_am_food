package com.am.amfood.ui.detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.am.amfood.R
import com.am.amfood.model.Product

class DetailViewModel : ViewModel() {
    private val _card = MutableLiveData<Product>()
    val card: LiveData<Product> = _card

    fun setValueProduct(cardModel: Product) {
        _card.value = cardModel
    }

    fun navigateToHome(navController: NavController) {
        navController.navigate(R.id.action_detailFragment_to_navigation_home)
    }

    fun navigateToMaps(latitude: Double?, longitude: Double?, context: Context) {
        val uri = Uri.parse("http://maps.google.com/maps?q=loc:${latitude},${longitude}")
        val mapIntent = Intent(Intent.ACTION_VIEW, uri)
        mapIntent.setPackage("com.google.android.apps.maps")
        context.startActivity(mapIntent)
    }
}