package com.am.amfood.ui.home

import android.app.Application
import android.content.Context
import android.widget.ImageView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.am.amfood.R
import com.am.amfood.adapter.MenuAdapter
import com.am.amfood.model.dummyDataCard

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val PREF_LAYOUT = "layout_pref"

    private val _isGrid = MutableLiveData<Boolean>()
    val isGrid: LiveData<Boolean> get() = _isGrid

    private val sharedPreferences =
        application.getSharedPreferences("menu_prefs", Context.MODE_PRIVATE)

    init {
        _isGrid.value = sharedPreferences.getBoolean(PREF_LAYOUT, true)
    }

    fun changeLayout() {
        val isGridLayout = _isGrid.value ?: true
        _isGrid.value = !isGridLayout

        sharedPreferences.edit().putBoolean(PREF_LAYOUT, !isGridLayout).apply()
    }

    fun setUpChangeIcon(imageView: ImageView, isGrid: Boolean) {
        val iconGrid = R.drawable.more
        val iconList = R.drawable.list
        imageView.setImageResource(if (isGrid) iconGrid else iconList)
    }

    fun setUpLayoutManager(context: Context, recyclerView: RecyclerView, isGrid: Boolean) {
        val layoutManager = if (isGrid) {
            GridLayoutManager(context, 2)
        } else {
            LinearLayoutManager(context)
        }
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = MenuAdapter(dummyDataCard, isGrid)
    }



}