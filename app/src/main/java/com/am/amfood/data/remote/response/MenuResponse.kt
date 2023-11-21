package com.am.amfood.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class MenuResponse(

	@field:SerializedName("data")
	val data: List<DataItem>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

@Parcelize
data class DataItem(

	@field:SerializedName("harga_format")
	val hargaFormat: String,

	@field:SerializedName("nama")
	val nama: String,

	@field:SerializedName("harga")
	val harga: Int,

	@field:SerializedName("image_url")
	val imageUrl: String,

	@field:SerializedName("detail")
	val detail: String,

	@field:SerializedName("alamat_resto")
	val alamatResto: String
): Parcelable
