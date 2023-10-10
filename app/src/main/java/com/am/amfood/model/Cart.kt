package com.am.amfood.model
import android.widget.ImageView
import androidx.room.Entity

@Entity(tableName = "Cart", primaryKeys = ["id"])
data class Cart(
    val id: Int? = null,
    var quantityMenu: Int,
    val nameMenu: String,
    val photoMenu : Int,
    var priceMenu : Double,
    var totalAmount : Double = 0.0,
    var note : String? = null
)



