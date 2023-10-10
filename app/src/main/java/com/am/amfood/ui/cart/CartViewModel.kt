package com.am.amfood.ui.cart

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.am.amfood.data.lokal.CartDatabase
import com.am.amfood.data.CartRepository
import com.am.amfood.model.Cart
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

    fun updateCart(cart: Cart) {
        viewModelScope.launch {
            try {
                repository.updateCart(cart)
                _messageToast.value = "Data Saved"
            } catch (e: Exception) {
                _messageToast.value = "Gagal Saved : $e"
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
        }
    }

    fun addCart(cart: Cart) {
        viewModelScope.launch {
            try {
                repository.insertCart(cart)
                _messageToast.value = "Data Saved"
            } catch (exc: Exception) {
                _messageToast.value = "Gagal Saved : $exc"
            }
        }
    }

    fun getAllCart(): LiveData<List<Cart>> = repository.getAllCart()

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