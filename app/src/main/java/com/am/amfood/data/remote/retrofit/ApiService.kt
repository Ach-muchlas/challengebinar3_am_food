package com.am.amfood.data.remote.retrofit

import com.am.amfood.data.remote.response.MenuResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService  {

    @GET("listmenu")
    fun getALlMenu() : Call<MenuResponse>
}