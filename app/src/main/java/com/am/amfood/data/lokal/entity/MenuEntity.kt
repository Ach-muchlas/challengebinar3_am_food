package com.am.amfood.data.lokal.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "menu")
@Parcelize
class MenuEntity(

    @field:ColumnInfo(name = "title")
    @field:PrimaryKey
    val title: String,

    @field:ColumnInfo(name = "priceString")
    val priceString: String,

    @field:ColumnInfo(name = "price")
    val price: Int,

    @field:ColumnInfo(name = "imageUrl")
    val imageUrl: String,

    @field:ColumnInfo(name = "like")
    var isLike: Boolean,

    @field:ColumnInfo(name = "description")
    var description: String,

    @field:ColumnInfo(name = "address")
    var address: String
) : Parcelable

