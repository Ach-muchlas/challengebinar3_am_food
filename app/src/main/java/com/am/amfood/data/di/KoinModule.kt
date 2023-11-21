package com.am.amfood.data.di

import com.am.amfood.data.lokal.room.DatabaseDb
import com.am.amfood.data.remote.retrofit.ApiConfig
import com.am.amfood.data.source.repository.AuthRepository
import com.am.amfood.data.source.repository.CartRepository
import com.am.amfood.data.source.repository.MenuRepository
import com.am.amfood.ui.auth.AuthViewModel
import com.am.amfood.ui.cart.CartViewModel
import com.am.amfood.ui.checkout.CheckOutViewModel
import com.am.amfood.ui.home.HomeViewModel
import com.am.amfood.ui.profile.ProfileViewModel
import com.am.amfood.utils.AppExecutors
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object KoinModule {
    val databaseModule
        get() = module {
            /*Api Service*/
            single { ApiConfig.getApiService() }
            /*Database*/
            single { DatabaseDb.getDatabaseInstance(get()) }
            /*Dao*/
            factory { get<DatabaseDb>().menuDao() }
            factory { get<DatabaseDb>().cartDao() }

            /*Repository*/
            factory { MenuRepository(get(), get(), get()) }
            factory { CartRepository(get(), get()) }
        }

    val firebaseModule
        get() = module {
            /*firebase auth*/
            single { FirebaseAuth.getInstance() }
            single { FirebaseDatabase.getInstance().reference }
            /*repository*/
            factory { AuthRepository(get(), get(), get()) }
        }

    val utilsModule
        get() = module {
            single { AppExecutors }
        }

    val uiModule
        get() = module {
            viewModel { HomeViewModel(get()) }
            viewModel { AuthViewModel(get()) }
            viewModel { ProfileViewModel(get()) }
            viewModel { CartViewModel(get()) }
            viewModel { CheckOutViewModel(get()) }
        }
}