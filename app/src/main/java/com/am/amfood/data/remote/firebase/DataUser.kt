package com.am.amfood.data.remote.firebase

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class DataUser(
    val email: String? = null,
    val phone: String? = null
)
