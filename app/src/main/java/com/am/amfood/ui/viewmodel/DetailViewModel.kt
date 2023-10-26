package com.am.amfood.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.am.amfood.data.lokal.entity.MenuEntity

class DetailViewModel : ViewModel() {
    private val _menu = MutableLiveData<MenuEntity>()
    val menu: LiveData<MenuEntity> = _menu

    private val _counter: MutableLiveData<Int> = MutableLiveData(0)
    val counter: LiveData<Int> = _counter

    fun setValueProduct(dataItem : MenuEntity) {
        _menu.value = dataItem
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