package com.am.amfood.data.source.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.liveData
import com.am.amfood.data.lokal.entity.MenuEntity
import com.am.amfood.data.lokal.room.MenuDao
import com.am.amfood.data.remote.response.MenuResponse
import com.am.amfood.data.remote.retrofit.ApiService
import com.am.amfood.data.source.Resource
import com.am.amfood.utils.AppExecutors
import kotlinx.coroutines.Dispatchers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MenuRepository(
    private val apiService: ApiService,
    private val menuDao: MenuDao,
    private val appExecutors: AppExecutors
) {
    private val result = MediatorLiveData<Resource<List<MenuEntity>>>()
    fun getListMenu(): LiveData<Resource<List<MenuEntity>>> {
        Resource.loading(null)
        val client = apiService.getALlMenu()
        client.enqueue(object : Callback<MenuResponse> {
            override fun onResponse(call: Call<MenuResponse>, response: Response<MenuResponse>) {
                if (response.isSuccessful) {
                    val menu = response.body()?.data
                    val dataMenuList = ArrayList<MenuEntity>()
                    appExecutors.diskIO.execute {
                        menu?.forEach { menu ->
                            val isLike = menuDao.isMenuLike(menu.nama)
                            val menuEntity = MenuEntity(
                                title = menu.nama,
                                imageUrl = menu.imageUrl,
                                price = menu.harga,
                                description = menu.detail,
                                address = menu.alamatResto,
                                priceString = menu.hargaFormat,
                                isLike = isLike
                            )
                            dataMenuList.add(menuEntity)
                        }
                        menuDao.deleteAllMenu()
                        menuDao.insertMenu(dataMenuList)
                    }
                }
            }

            override fun onFailure(call: Call<MenuResponse>, t: Throwable) {
                Resource.error(data = null, message = t.message ?: "Error")
            }
        })
        val localData = menuDao.getMenuFromDatabase()
        result.addSource(localData) { newData: List<MenuEntity> ->
            result.value = Resource.success(newData)
        }
        return result
    }

    fun getLike(): LiveData<List<MenuEntity>> {
        return menuDao.getMenuLikeFromDatabase()
    }

    fun setIsLike(menu: MenuEntity, likeState: Boolean) {
        appExecutors.diskIO.execute {
            menu.isLike = likeState
            menuDao.updateMenu(menu)
        }
    }

    fun getCategoryMenu() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(data = apiService.getCategoryMenu()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!!"))
        }
    }

    companion object {
        @Volatile
        private var instance: MenuRepository? = null
        fun getInstance(
            apiService: ApiService,
            menuDao: MenuDao,
            appExecutors: AppExecutors
        ): MenuRepository =
            instance ?: synchronized(this) {
                instance ?: MenuRepository(apiService, menuDao, appExecutors)
            }.also { instance = it }
    }
}