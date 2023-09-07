package com.am.amfood.model
import com.am.amfood.R

data class CardModel(
    val name : String,
    val rate : String,
    val price : String,
    val photo : Int
)

val dummyDataCard = mutableListOf(
    CardModel("Beef Burger", "5.0", "$ 17,80", R.drawable.chicken_burger),
    CardModel("Chicken Burger", "5.0", "$ 16,20", R.drawable.chicken_burger),
    CardModel("Cheese Burger", "4.9", "$ 10,80", R.drawable.cheese_burger),
    CardModel("Fish Burger", "4.6", "$ 20,90", R.drawable.cheese_burger)
)

