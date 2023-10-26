package com.am.amfood.data.source

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.am.amfood.data.lokal.entity.MenuEntity
import com.am.amfood.data.lokal.room.MenuDao
import com.am.amfood.data.remote.retrofit.ApiService
import kotlinx.coroutines.Dispatchers

class MenuRepository private constructor(
    private val apiService: ApiService,
    private val menuDao: MenuDao,
) {

    fun getMenu() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiService.getListMenu()
            val dataItemMenu = response.data!!
            val newListMenu = dataItemMenu.map { dataItem ->
                val isLike = menuDao.isMenuLike(dataItem.nama)
                MenuEntity(
                    dataItem.nama,
                    dataItem.hargaFormat,
                    dataItem.harga,
                    dataItem.imageUrl,
                    isLike,
                    dataItem.detail,
                    dataItem.alamatResto
                )
            }
            menuDao.deleteAllMenu()
            menuDao.insertMenu(newListMenu)
        } catch (exception: Exception) {
            Log.e("Repository", "getMenu: ${exception.message}")
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!!"))
        }
        val localData = menuDao.getMenuFromDatabase().map { Resource.success(data = it) }
        emitSource(localData)
    }

    fun getLike(): LiveData<List<MenuEntity>> {
        return menuDao.getMenuLikeFromDatabase()
    }

    suspend fun setIsLike(menu: MenuEntity, likeState: Boolean) {
        menu.isLike = likeState
        menuDao.updateMenu(menu)
    }

    fun getCategoryMenu() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(data = apiService.getCategoryMenu()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!!"))
        }
    }


//    suspend fun getAllDataCart(): LiveData<List<Cart>> = cartDao.getAllCart()
//    suspend fun getTotalPayment() : LiveData<Double> = cartDao.getTotalPayment()
//    suspend fun addOrUpdateCart(cart: Cart) = cartDao.addCartOrUpdate(cart)
//    suspend fun deleteItemCart(cart: Cart) = cartDao.delete(cart)
//    suspend fun updateItemCart(cart: Cart) = cartDao.update(cart)
//    suspend fun deleteAllDataCart(cart: Cart) = cartDao.deleteAll()


    companion object {
        @Volatile
        private var instance: MenuRepository? = null
        fun getInstance(
            apiService: ApiService,
            menuDao: MenuDao,
        ): MenuRepository =
            instance ?: synchronized(this) {
                instance ?: MenuRepository(apiService, menuDao)
            }.also { instance = it }
    }
}