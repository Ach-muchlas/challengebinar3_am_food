package com.am.amfood.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.am.amfood.data.remote.model.Product

class DetailViewModel : ViewModel() {
    private val _card = MutableLiveData<Product>()
    val card: LiveData<Product> = _card

    private val _counter: MutableLiveData<Int> = MutableLiveData(0)
    val counter: LiveData<Int> = _counter

    fun setValueProduct(cardModel: Product) {
        _card.value = cardModel
    }

    fun incrementCountQuantity() {
        _counter.postValue(_counter.value?.plus(1))
    }

    fun decrementCountQuantity() {
        _counter.value?.let {
            if (it > 0) {
                _counter.postValue(_counter.value?.minus(1))
            }
        }
    }
}