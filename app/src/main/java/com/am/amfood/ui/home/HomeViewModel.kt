package com.am.amfood.ui.home

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.am.amfood.R
import com.am.amfood.data.remote.response.DataItem
import com.am.amfood.data.remote.response.MenuResponse
import com.am.amfood.data.remote.retrofit.ApiConfig
import com.am.amfood.ui.adapter.MenuAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val _isGrid = MutableLiveData<Boolean>()
    val isGrid: LiveData<Boolean> get() = _isGrid

    private val _allMenu = MutableLiveData<List<DataItem>>()
    val allMenu: LiveData<List<DataItem>> = _allMenu

    private val sharedPreferences =
        application.getSharedPreferences("menu_prefs", Context.MODE_PRIVATE)

    init {
        _isGrid.value = sharedPreferences.getBoolean(PREF_LAYOUT, true)
        getAllMenu()
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

    fun setUpLayoutManager(
        context: Context,
        recyclerView: RecyclerView,
        isGrid: Boolean,
        menu: List<DataItem>
    ) {
        val layoutManager = if (isGrid) {
            GridLayoutManager(context, 2)
        } else {
            LinearLayoutManager(context)
        }
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = MenuAdapter(menu, isGrid)
    }

    fun getAllMenu() {
        val client = ApiConfig.getApiService().getALlMenu()

        client.enqueue(object : Callback<MenuResponse> {
            override fun onResponse(call: Call<MenuResponse>, response: Response<MenuResponse>) {
                if (response.isSuccessful) {
                    _allMenu.value = response.body()?.data as List<DataItem>
                } else {
                    Log.e(TAG, "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<MenuResponse>, t: Throwable) {
                Log.e(TAG, "onFailure : ${t.message}")
            }

        })
    }

    companion object {
        const val PREF_LAYOUT = "layout_pref"
        const val TAG = "HomeViewModel"
    }


}