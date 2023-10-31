package com.am.amfood.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.am.amfood.data.lokal.entity.Cart
import com.am.amfood.data.source.repository.CartRepository
import kotlinx.coroutines.launch

class CartViewModel(private val repository: CartRepository) : ViewModel() {
    private val _messageToast = MutableLiveData("")
    val messageToast: LiveData<String> = _messageToast
    fun getAllCartaData() = repository.getDataCart()
    fun getTotalPaymentData() = repository.getDataTotalPayment()

    fun addDataOrUpdateCartData(cart: Cart) {
        viewModelScope.launch {
            try {
                repository.addDataOrUpdateCartData(cart)
                _messageToast.value = "Successful send data"
            } catch (exception: Exception) {
                _messageToast.value = "Error Occurred send data ${exception.message}"
            }
        }
    }
    private fun updateCart(cart: Cart) {
        viewModelScope.launch {
            try {
                repository.updateCart(cart)
                _messageToast.value = "Successful Update Data"
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
            if (cart.quantityMenu == 0) {
                deleteItem(cart)
            }
        }
    }

    fun deleteItem(cart: Cart) {
        viewModelScope.launch {
            repository.deleteItem(cart)
        }
    }

    fun deleteDataCart() {
        viewModelScope.launch {
            repository.deleteAll()
        }
    }
}