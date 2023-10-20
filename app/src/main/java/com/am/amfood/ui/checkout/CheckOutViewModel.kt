package com.am.amfood.ui.checkout

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.am.amfood.data.remote.response.OrderResponse
import com.am.amfood.data.remote.response.OrdersItem
import com.am.amfood.data.remote.retrofit.ApiConfig
import com.am.amfood.data.source.Result

class CheckOutViewModel : ViewModel() {

    fun checkOutOrder(
        username: String,
        total: Int,
        order: OrdersItem
    ): LiveData<Result<OrderResponse>> = liveData {
        val orderItem = OrderResponse(total, listOf(order), username)
        try {
            val response = ApiConfig.getApiService().orderMenu(orderItem)
            emit(Result.Success(response))
        }catch (e : Exception){
            emit(Result.Error("Error : ${e.message}"))
        }
    }

}