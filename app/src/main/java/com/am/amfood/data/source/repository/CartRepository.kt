package com.am.amfood.data.source.repository

import androidx.lifecycle.liveData
import com.am.amfood.data.lokal.entity.Cart
import com.am.amfood.data.lokal.room.CartDao
import com.am.amfood.data.remote.response.OrderResponse
import com.am.amfood.data.remote.response.OrdersItem
import com.am.amfood.data.remote.retrofit.ApiService
import com.am.amfood.data.source.Resource
import kotlinx.coroutines.Dispatchers

class CartRepository(
    private val apiService: ApiService,
    private val dao: CartDao
) {
    fun getDataCart() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(data = dao.getAllCart()))
        } catch (exception: Exception) {
            emit(
                Resource.error(
                    data = null,
                    message = exception.message ?: "Error Occurred!!"
                )
            )
        }
    }
    fun getDataTotalPayment() = dao.getTotalPayment()

    suspend fun addDataOrUpdateCartData(cart: Cart) = dao.addCartOrUpdate(cart)

    suspend fun deleteItem(cart: Cart) {
        return dao.delete(cart)
    }

    suspend fun updateCart(cart: Cart) {
        return dao.update(cart)
    }

    suspend fun deleteAll() {
        return dao.deleteAll()
    }

    fun postCheckOutOrder(
        username: String,
        total: Int,
        order: OrdersItem
    ) = liveData(Dispatchers.IO) {
        val orderItem = OrderResponse(total, listOf(order), username)
        emit(Resource.loading(null))
        try {
            emit(Resource.success(data = apiService.orderMenu(orderItem)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!!"))
        }
    }
}