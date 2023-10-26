package com.am.amfood.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.am.amfood.data.lokal.entity.MenuEntity
import com.am.amfood.data.source.MenuRepository
import com.am.amfood.data.source.Preferences
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: MenuRepository) :
    ViewModel() {

    private val preferences = Preferences
    val isGridlayout: LiveData<Boolean> get() = preferences.isGrid

    fun setUpIsGridLayout() {
        preferences.setIsGridLayout()
    }

    fun getListMenu() = repository.getMenu()
    fun getLikeMenu() = repository.getLike()

    fun getCategoryMenu() = repository.getCategoryMenu()
    fun saveLike(menu: MenuEntity) {
        viewModelScope.launch {
            repository.setIsLike(menu, true)
        }
    }

    fun deleteLike(menu: MenuEntity) {
        viewModelScope.launch {
            repository.setIsLike(menu, false)
        }
    }
}