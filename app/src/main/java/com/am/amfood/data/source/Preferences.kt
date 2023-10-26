package com.am.amfood.data.source

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.lang.Exception

object Preferences {

    private lateinit var sharePref : SharedPreferences
    private const val PREF_LAYOUT = "layout_pref"
    private val _isGrid = MutableLiveData<Boolean>()
    val isGrid: LiveData<Boolean> get() = _isGrid

    fun init(context: Context){
        try {
            sharePref = context.getSharedPreferences("menu_pref", Context.MODE_PRIVATE)
        }catch (exception : Exception){
            Log.e("SIMPLEMAIN", "error ${exception.message.toString()}")
        }
    }

    init {
        _isGrid.value = sharePref.getBoolean(PREF_LAYOUT, true)
    }

    fun setIsGridLayout() {
        val isGridLayout = _isGrid.value ?: true
        _isGrid.value = !isGridLayout
        sharePref.edit().putBoolean(PREF_LAYOUT, !isGridLayout).apply()
    }

}