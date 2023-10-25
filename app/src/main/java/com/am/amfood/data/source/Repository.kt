package com.am.amfood.data.source

import androidx.lifecycle.LiveData
import com.am.amfood.data.lokal.entity.Cart
import com.am.amfood.data.lokal.room.CartDao
import com.am.amfood.data.remote.retrofit.ApiService

class Repository private constructor(
    private val apiService: ApiService,
    private val cartDao: CartDao,
    val preferences: Preferences
) {
    suspend fun getListMenu() = apiService.getListMenu()
    suspend fun getCategoryMenu() = apiService.getCategoryMenu()

    suspend fun getAllDataCart(): LiveData<List<Cart>> = cartDao.getAllCart()
    suspend fun getTotalPayment() : LiveData<Double> = cartDao.getTotalPayment()
    suspend fun addOrUpdateCart(cart: Cart) = cartDao.addCartOrUpdate(cart)
    suspend fun deleteItemCart(cart: Cart) = cartDao.delete(cart)
    suspend fun updateItemCart(cart: Cart) = cartDao.update(cart)
    suspend fun deleteDataCart(cart: Cart) = cartDao.deleteAll()


    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            apiService: ApiService,
            cartDao: CartDao,
            preferences: Preferences
        ): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(apiService, cartDao, preferences)
            }.also { instance = it }
    }
}