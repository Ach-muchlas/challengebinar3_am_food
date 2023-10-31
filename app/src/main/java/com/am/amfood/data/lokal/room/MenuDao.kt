package com.am.amfood.data.lokal.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.am.amfood.data.lokal.entity.MenuEntity

@Dao
interface MenuDao {
    @Query("SELECT * FROM menu ORDER BY title DESC")
    fun getMenuFromDatabase(): LiveData<List<MenuEntity>>

    @Query("SELECT * FROM menu where `like`= 1")
    fun getMenuLikeFromDatabase(): LiveData<List<MenuEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
     fun insertMenu(menu: List<MenuEntity>)

    @Query("DELETE FROM menu WHERE `like` = 0")
     fun deleteAllMenu()

    @Update
     fun updateMenu(menu: MenuEntity)

    @Query("SELECT EXISTS(SELECT * FROM menu WHERE title = :title AND `like` = 1)")
    fun isMenuLike(title: String): Boolean
}