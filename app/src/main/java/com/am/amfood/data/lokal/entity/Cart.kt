package com.am.amfood.data.lokal.entity
import android.widget.ImageView
import androidx.room.Entity

@Entity(tableName = "Cart", primaryKeys = ["id"])
data class Cart(
    val id: Int? = null,
    var quantityMenu: Int,
    val nameMenu: String,
    val photoMenu : String,
    var priceMenu : Double,
    var totalAmount : Double = 0.0,
    var note : String? = null
)



