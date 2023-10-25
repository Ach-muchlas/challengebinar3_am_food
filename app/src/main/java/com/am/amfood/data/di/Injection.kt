package com.am.amfood.data.di

import android.content.Context
import com.am.amfood.data.lokal.room.CartDatabase
import com.am.amfood.data.remote.retrofit.ApiConfig
import com.am.amfood.data.source.Preferences
import com.am.amfood.data.source.Repository

object Injection {
    fun provideRepository(context: Context): Repository {
        val apiService = ApiConfig.getApiService()
        val database = CartDatabase.getDatabaseInstance(context)
        val dao = database.cartDao()
        val preferences = Preferences(context)
        return Repository.getInstance(apiService, dao, preferences)
    }
}