package com.am.amfood.data.lokal.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.am.amfood.data.lokal.entity.Cart

@Database(entities = [Cart::class], version = 5)
abstract class CartDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao

    companion object {
        @Volatile
        var INSTANCE: CartDatabase? = null

        fun getDatabaseInstance(context: Context): CartDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val roomDatabaseInstance =
                    Room.databaseBuilder(context, CartDatabase::class.java, "Cart")
                        .allowMainThreadQueries().fallbackToDestructiveMigration()
                        .build()
                INSTANCE = roomDatabaseInstance
                return roomDatabaseInstance
            }
        }
    }
}