package com.am.amfood.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.am.amfood.data.lokal.entity.Cart
import com.am.amfood.data.source.repository.CartRepository
import kotlinx.coroutines.launch

class CartViewModel(private val repository: CartRepository) : ViewModel() {

    /*to accommodate messages*/
    private val _messageToast = MutableLiveData("")
    val messageToast: LiveData<String> = _messageToast

    /*function is used to receive cart data in the cart database*/
    fun getAllCartaData() = repository.getAllDataCart()

    /*function is used to receive total payment in the cart database*/
    fun getTotalPaymentData() = repository.getDataTotalPayment()

    /*function is used is used to check data additions or updates to cart database*/
    fun addDataOrUpdateCartData(cart: Cart) {
        viewModelScope.launch {
            try {
                repository.addDataOrUpdateCartData(cart)
                _messageToast.postValue("Successful send data")
            } catch (exception: Exception) {
                _messageToast.value = "Error Occurred send data ${exception.message}"
            }
        }
    }

    /*function is used update data cart in cart database*/
    fun updateCart(cart: Cart) {
        viewModelScope.launch {
            try {
                repository.updateCart(cart)
                _messageToast.value = "Successful Update Data"
            } catch (e: Exception) {
                _messageToast.value = "Data failed to save : ${e.message}"
            }
        }
    }

    /*function is add quantity and change total payment*/
    fun increment(cart: Cart) {
        cart.quantityMenu++
        cart.totalAmount = cart.priceMenu * cart.quantityMenu
        updateCart(cart)
    }

    /*function to reduce quantity and change total payment*/
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

    /*function is used delete item cart*/
    fun deleteItem(cart: Cart) {
        viewModelScope.launch {
            repository.deleteItem(cart)
        }
    }

    /*function is used delete all data cart */
    fun deleteDataCart() {
        viewModelScope.launch {
            repository.deleteAll()
        }
    }
}