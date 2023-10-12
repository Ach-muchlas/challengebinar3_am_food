package com.am.amfood.data.remote.response

import com.google.gson.annotations.SerializedName

data class MenuResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class DataItem(

	@field:SerializedName("harga_format")
	val hargaFormat: String? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("harga")
	val harga: Int? = null,

	@field:SerializedName("image_url")
	val imageUrl: String? = null,

	@field:SerializedName("detail")
	val detail: String? = null,

	@field:SerializedName("alamat_resto")
	val alamatResto: String? = null
)
