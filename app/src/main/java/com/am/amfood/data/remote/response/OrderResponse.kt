package com.am.amfood.data.remote.response

import com.google.gson.annotations.SerializedName

data class OrderResponse(

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("orders")
	val orders: List<OrdersItem?>? = null,

	@field:SerializedName("username")
	val username: String? = null
)

data class OrdersItem(

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("harga")
	val harga: Int? = null,

	@field:SerializedName("qty")
	val qty: Int? = null,

	@field:SerializedName("catatan")
	val catatan: String? = null
)
