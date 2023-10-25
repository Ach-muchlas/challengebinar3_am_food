//package com.am.amfood.data.di
//
//import android.app.Application
//import com.am.amfood.data.lokal.room.CartDao
//import com.am.amfood.data.lokal.room.CartDatabase
//import com.am.amfood.data.remote.retrofit.ApiConfig
//import com.am.amfood.data.remote.retrofit.ApiService
//import com.am.amfood.data.source.Preferences
//import dagger.Module
//import dagger.Provides
//import javax.inject.Singleton
//
//@Module
//class AppModule(private val application: Application) {
//
//    @Provides
//    @Singleton
//    fun provideApplication(): Application {
//        return application
//    }
//
//    @Provides
//    @Singleton
//    fun provideApiService(): ApiService {
//        return ApiConfig.getApiService()
//    }
//
//    @Provides
//    @Singleton
//    fun provideDao(application: Application): CartDao {
//        val database = CartDatabase.getDatabaseInstance(application)
//        return database.cartDao()
//    }
//
//    @Provides
//    @Singleton
//    fun providePreferences(application: Application): Preferences {
//        return Preferences(application)
//    }
//}