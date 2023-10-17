package com.am.amfood.data.lokal.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.am.amfood.data.lokal.entity.Cart

@Dao
interface CartDao {

    @Query("Select * from Cart")
    fun getAllCart(): LiveData<List<Cart>>

    @Transaction
    fun addCartToUpdate(cart: Cart) {
        val existingCart = getOrderById(cart.nameMenu)
        if (existingCart != null){
            val newQuantity = existingCart.quantityMenu + cart.quantityMenu
            existingCart.quantityMenu = newQuantity
            update(existingCart)
        }else{
            insert(cart)
        }
    }

    @Query("Select * FROM cart WHERE nameMenu = :name")
    fun getOrderById(name : String): Cart?

    @Query("Select SUM(priceMenu * quantityMenu) FROM cart")
    fun getTotalPayment(): LiveData<Double>

    @Query("DELETE FROM cart")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cart: Cart)

    @Delete
    fun delete(cart: Cart)

    @Update
    fun update(cart: Cart)

}