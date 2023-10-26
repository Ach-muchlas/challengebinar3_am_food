package com.am.amfood.data.di

import android.app.Application
import com.am.amfood.data.source.Preferences

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Preferences.init(applicationContext)
    }
}