package com.am.amfood.data.source

import androidx.lifecycle.LiveData
import com.am.amfood.data.lokal.entity.Cart
import com.am.amfood.data.lokal.room.CartDao

class CartRepository(private val dao: CartDao) {
    suspend fun getAllCart(): LiveData<List<Cart>> {
        return dao.getAllCart()
    }

    fun getTotalPayment(): LiveData<Double> {
        return dao.getTotalPayment()
    }

    suspend fun addCartToUpdate(cart: Cart) {
        return dao.addCartOrUpdate(cart)
    }

    suspend fun deleteItem(cart: Cart) {
        return dao.delete(cart)
    }

    suspend fun updateCart(cart: Cart) {
        return dao.update(cart)
    }

    suspend fun deleteAll() {
        return dao.deleteAll()
    }
}