package com.am.amfood.ui.checkout

import androidx.lifecycle.ViewModel
import com.am.amfood.data.remote.response.OrdersItem
import com.am.amfood.data.source.repository.CartRepository

class CheckOutViewModel(private val repository: CartRepository) : ViewModel() {
    fun checkOutOrder(
        username: String,
        total: Int,
        order: OrdersItem
    ) = repository.postCheckOutOrder(username, total, order)
}