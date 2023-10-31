package com.am.amfood.data.remote.firebase

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class DataUser(
    val username :String? = null,
    val email: String? = null,
    val phone: String? = null,
    val passwordUid : String? = null,
    val imageUrl : String? = null
)
