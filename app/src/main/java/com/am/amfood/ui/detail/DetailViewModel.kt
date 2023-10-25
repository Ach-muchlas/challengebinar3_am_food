package com.am.amfood.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.am.amfood.data.remote.response.DataItem

class DetailViewModel : ViewModel() {
    private val _menu = MutableLiveData<DataItem>()
    val menu: LiveData<DataItem> = _menu

    private val _counter: MutableLiveData<Int> = MutableLiveData(0)
    val counter: LiveData<Int> = _counter

    fun setValueProduct(dataItemMenu: DataItem) {
        _menu.value = dataItemMenu
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