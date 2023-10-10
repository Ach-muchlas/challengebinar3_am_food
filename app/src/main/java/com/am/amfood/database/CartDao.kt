package com.am.amfood.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.am.amfood.model.Cart

@Dao
interface CartDao {

    @Query("Select * from Cart")
    fun getAllCart(): LiveData<List<Cart>>

    @Query("Select SUM(priceMenu * quantityMenu) FROM cart")
    fun getTotalPayment() : LiveData<Double>

    @Query("DELETE FROM cart")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cart: Cart)

    @Delete
    fun delete(cart: Cart)

    @Update
    fun update(cart: Cart)

}