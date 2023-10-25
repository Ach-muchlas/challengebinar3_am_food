package com.am.amfood.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.am.amfood.data.source.Repository
import com.am.amfood.data.source.Resource
import kotlinx.coroutines.Dispatchers

class HomeViewModel(private val repository: Repository) :
    ViewModel() {

    private val preferences = repository.preferences
    val isGridlayout: LiveData<Boolean> get() = preferences.isGrid

    fun setUpIsGridLayout() {
        preferences.setIsGridLayout()
    }

    fun getListMenu() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(data = repository.getListMenu()))
        } catch (exception: Exception) {
            emit(
                Resource.error(
                    data = null,
                    message = exception.message ?: "Error Occurred!!"
                )
            )
        }
    }

    fun getListCategoryMenu() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(data = repository.getCategoryMenu()))
        } catch (exception: Exception) {
            emit(
                Resource.error(
                    data = null,
                    message = exception.message ?: "Error Occurred!!"
                )
            )
        }
    }

}