package com.am.amfood.model

import androidx.room.Entity

@Entity(tableName = "Cart")
data class Cart(
    val id : Int,
    val menu : Product,
    val quantityMenu: Int,
)


