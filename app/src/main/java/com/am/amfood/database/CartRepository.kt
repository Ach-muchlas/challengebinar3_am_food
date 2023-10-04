package com.am.amfood.database

import androidx.lifecycle.LiveData
import com.am.amfood.model.Cart

class CartRepository(private val dao: CartDao) {
    fun getAllCart(): LiveData<List<Cart>> {
        return dao.getAllCart()
    }

    fun insertCart(cart: Cart) {
        return dao.insert(cart)
    }

    fun deleteCart(cart: Cart) {
        return dao.delete(cart)
    }
}