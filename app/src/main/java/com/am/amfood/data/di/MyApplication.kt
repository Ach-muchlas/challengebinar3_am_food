package com.am.amfood.data.di

import android.app.Application
import com.am.amfood.data.di.KoinModule.databaseModule
import com.am.amfood.data.di.KoinModule.firebaseModule
import com.am.amfood.data.di.KoinModule.uiModule
import com.am.amfood.data.di.KoinModule.utilsModule
import com.am.amfood.data.source.sharedpreferences.Preferences
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        Preferences.getInstance().init(context = this)
        super.onCreate()
        startKoin{
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    firebaseModule,
                    utilsModule,
                    uiModule
                )
            )
        }
    }
}