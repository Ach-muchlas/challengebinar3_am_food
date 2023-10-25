package com.am.amfood.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.am.amfood.data.source.Repository
import com.am.amfood.data.source.Resource
import kotlinx.coroutines.Dispatchers

class CartViewModel(private val repository: Repository) : ViewModel() {

    fun getDataCart() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(data = repository.getAllDataCart()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!!"))
        }
    }

    fun getTotalPayment() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(data = repository.getTotalPayment()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!!"))
        }
    }

}