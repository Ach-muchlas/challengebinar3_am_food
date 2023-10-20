package com.am.amfood.data.remote.response

import com.google.gson.annotations.SerializedName

data class CategoryResponse(

    @field:SerializedName("data")
    val data: List<DataItemCategory>? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: Boolean? = null
)

data class DataItemCategory(

    @field:SerializedName("createdAt")
    val createdAt: String? = null,

    @field:SerializedName("nama")
    val nama: String? = null,

    @field:SerializedName("imageUrl")
    val imageUrl: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("updatedAt")
    val updatedAt: String? = null
)
