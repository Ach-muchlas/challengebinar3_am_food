package com.am.amfood.data.source

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class Preferences(context: Context) {
    private val sharePref = context.getSharedPreferences("menu_pref", Context.MODE_PRIVATE)
    private val _isGrid = MutableLiveData<Boolean>()
    val isGrid: LiveData<Boolean> get() = _isGrid

    init {
        _isGrid.value = sharePref.getBoolean(PREF_LAYOUT, true)
    }

    fun setIsGridLayout() {
        val isGridLayout = _isGrid.value ?: true
        _isGrid.value = !isGridLayout
        sharePref.edit().putBoolean(PREF_LAYOUT, !isGridLayout).apply()
    }

    companion object {
        const val PREF_LAYOUT = "layout_pref"
    }
}