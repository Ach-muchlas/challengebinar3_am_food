package com.am.amfood.data.di

import android.content.Context
import com.am.amfood.data.lokal.room.CartDatabase
import com.am.amfood.data.remote.retrofit.ApiConfig
import com.am.amfood.data.source.Preferences
import com.am.amfood.data.source.MenuRepository

object Injection {
    fun provideRepository(context: Context): MenuRepository {
        val apiService = ApiConfig.getApiService()
        val database = CartDatabase.getDatabaseInstance(context)
        val menuDao = database.menuDao()
        return MenuRepository.getInstance(apiService, menuDao)
    }
}