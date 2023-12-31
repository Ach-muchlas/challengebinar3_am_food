package com.am.amfood.data.remote.retrofit

import com.am.amfood.data.remote.response.CategoryResponse
import com.am.amfood.data.remote.response.MenuResponse
import com.am.amfood.data.remote.response.OrderResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService  {
    @GET("listmenu")
    fun getALlMenu() : Call<MenuResponse>
    @GET("category")
    suspend fun getCategoryMenu() : CategoryResponse

    @POST("order-menu")
    suspend fun orderMenu(@Body orderResponse : OrderResponse) : OrderResponse
}