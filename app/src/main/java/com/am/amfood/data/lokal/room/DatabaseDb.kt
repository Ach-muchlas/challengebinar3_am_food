package com.am.amfood.data.lokal.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.am.amfood.data.lokal.entity.Cart
import com.am.amfood.data.lokal.entity.MenuEntity

@Database(entities = [Cart::class, MenuEntity::class], version = 9)
abstract class DatabaseDb : RoomDatabase() {
    abstract fun cartDao(): CartDao
    abstract fun menuDao(): MenuDao


    companion object {
        @Volatile
        var INSTANCE: DatabaseDb? = null

        fun getDatabaseInstance(context: Context): DatabaseDb {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val roomDatabaseInstance =
                    Room.databaseBuilder(context, DatabaseDb::class.java, "DatabaseDB")
                        .allowMainThreadQueries().fallbackToDestructiveMigration()
                        .build()
                INSTANCE = roomDatabaseInstance
                return roomDatabaseInstance
            }
        }
    }
}