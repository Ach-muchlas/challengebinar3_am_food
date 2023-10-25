package com.am.amfood.ui.cart

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.am.amfood.data.lokal.entity.Cart
import com.am.amfood.data.lokal.room.CartDatabase
import com.am.amfood.data.source.CartRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CartViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: CartRepository
    private val _messageToast = MutableLiveData("")
    val messageToast: LiveData<String> = _messageToast


    init {
        val dao = CartDatabase.getDatabaseInstance(application).cartDao()
        repository = CartRepository(dao)
    }

    fun getTotalPayment(): LiveData<Double> {
        return repository.getTotalPayment()
    }

    private fun updateCart(cart: Cart) {
        viewModelScope.launch {
            try {
                repository.updateCart(cart)
                _messageToast.value = "Data Saved Successfully"
            } catch (e: Exception) {
                _messageToast.value = "Data failed to save : ${e.message}"
            }
        }
    }



    fun increment(cart: Cart) {
        cart.quantityMenu++
        cart.totalAmount = cart.priceMenu * cart.quantityMenu
        updateCart(cart)
    }

    fun decrement(cart: Cart) {
        if (cart.quantityMenu > 0) {
            cart.quantityMenu--
            cart.totalAmount = cart.priceMenu * cart.quantityMenu
            updateCart(cart)
            if (cart.quantityMenu == 0){
                deleteItem(cart)
            }
        }
    }

    fun addCartToUpdate(cart: Cart){
        viewModelScope.launch {
            try {
                repository.addCartToUpdate(cart)
                _messageToast.value = "Data Saved Successfully"
            }catch (e : Exception){
                _messageToast.value = "Data Failed to Save : ${e.message}"
            }
        }
    }

//    fun getAllCart(): LiveData<List<Cart>> = repository.getAllCart()

    fun deleteItem(cart: Cart) {
        repository.deleteItem(cart)
    }

    fun deleteDataCart() {
        viewModelScope.launch {
            repository.deleteAll()
        }
    }
}