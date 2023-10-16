package com.am.amfood.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class MenuResponse(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

@Parcelize
data class DataItem(

	@field:SerializedName("hargaFormat")
	val hargaFormat: String? = null,

	@field:SerializedName("nama")
	val nama: String,

	@field:SerializedName("harga")
	val harga: Int,

	@field:SerializedName("imageUrl")
	val imageUrl: String,

	@field:SerializedName("detail")
	val detail: String? = null,

	@field:SerializedName("alamatResto")
	val alamatResto: String? = null
): Parcelable
