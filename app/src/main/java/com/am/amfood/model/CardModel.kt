package com.am.amfood.model
import android.os.Parcelable
import com.am.amfood.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class CardModel(
    val name : String,
    val rate : String,
    val price : String,
    val photo : Int,
    val desc : String? = null
) : Parcelable

val dummyDataCard = arrayListOf<CardModel>(
    CardModel("Beef Burger", "5.0", "$ 17,80", R.drawable.chicken_burger),
    CardModel("Chicken Burger", "5.0", "$ 16,20", R.drawable.chicken_burger),
    CardModel("Cheese Burger", "4.9", "$ 10,80", R.drawable.cheese_burger),
    CardModel("Fish Burger", "4.6", "$ 20,90", R.drawable.cheese_burger)
)

