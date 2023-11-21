package com.am.amfood.data.remote.response

import com.google.gson.annotations.SerializedName

data class CategoryResponse(

    @field:SerializedName("code")
    val code: Int? = null,

    @field:SerializedName("data")
    val data: List<DataItemCategory>? = null,


    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: Boolean? = null
)

data class DataItemCategory(

    @field:SerializedName("nama")
    val nama: String? = null,

    @field:SerializedName("image_url")
    val imageUrl: String? = null,
)
