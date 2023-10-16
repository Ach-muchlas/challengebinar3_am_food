package com.am.amfood.data.remote.retrofit

import com.am.amfood.data.remote.response.MenuResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService  {
    @GET("listmenu")
    fun getALlMenu() : Call<MenuResponse>

    @GET("listmenu/")
    fun getMenuCategory(
        @Query("c") c : String
    ) : Call<MenuResponse>
}