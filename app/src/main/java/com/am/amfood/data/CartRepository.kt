package com.am.amfood.data

import androidx.lifecycle.LiveData
import com.am.amfood.data.lokal.CartDao
import com.am.amfood.model.Cart

class CartRepository(private val dao: CartDao) {
    fun getAllCart(): LiveData<List<Cart>> {
        return dao.getAllCart()
    }

    fun getTotalPayment() : LiveData<Double>{
        return dao.getTotalPayment()
    }

    fun insertCart(cart: Cart) {
        return dao.insert(cart)
    }

    fun deleteItem(cart: Cart) {
        return dao.delete(cart)
    }
    fun updateCart(cart: Cart){
        return dao.update(cart)
    }

    fun deleteAll(){
        return dao.deleteAll()
    }
}