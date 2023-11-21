package com.am.amfood.data.source.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class Preferences private constructor() {

    private lateinit var sharePref: SharedPreferences
    private val _isGrid = MutableLiveData<Boolean>()
    val isGrid: LiveData<Boolean> get() = _isGrid

    fun init(context: Context) {
        sharePref = context.getSharedPreferences("menu_pref", Context.MODE_PRIVATE)
        _isGrid.value = sharePref.getBoolean(PREF_LAYOUT, true)
    }

    fun setIsGridLayout() {
        val isGridLayout = _isGrid.value ?: true
        _isGrid.value = !isGridLayout
        sharePref.edit().putBoolean(PREF_LAYOUT, !isGridLayout).apply()
    }

    companion object {
        @Volatile
        private var instance: Preferences? = null

        fun getInstance(): Preferences {
            return instance ?: synchronized(this) {
                instance ?: Preferences().also { instance = it }
            }
        }

        private const val PREF_LAYOUT = "layout_pref"
    }

}