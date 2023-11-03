package com.am.amfood.data.source.repository

import androidx.lifecycle.LiveData
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

    /*function for get data cart in database room*/
    fun getAllDataCart(): LiveData<List<Cart>> = dao.getAllCart()

    /*function for get total payment user*/
    fun getDataTotalPayment() = dao.getTotalPayment()

    /*function to add or update data that is already in the basket*/
    suspend fun addDataOrUpdateCartData(cart: Cart) = dao.addCartOrUpdate(cart)

    /*function used to delete data cart item*/
    suspend fun deleteItem(cart: Cart) = dao.delete(cart)

    /*function used to update data cart*/
    fun updateCart(cart: Cart) = dao.update(cart)

    /*function used to delete all data cart*/
    suspend fun deleteAll() = dao.deleteAll()

    /*function used to checkout user*/
    fun postCheckOutOrder(
        username: String, total: Int, order: OrdersItem
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