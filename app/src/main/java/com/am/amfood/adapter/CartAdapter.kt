package com.am.amfood.adapter

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.am.amfood.databinding.ItemCartBinding
import com.am.amfood.model.Cart
import com.am.amfood.utils.Utils.formatCurrency

class CartAdapter(
    context: Context,
    private val data: List<Cart>,
    private var onDeleteClickListener: ((Cart) -> Unit)? = null,
    private var onPlusClickLister: ((Cart) -> Unit)? = null,
    private var onMinusClickLister: ((Cart) -> Unit)? = null,
    private var addNote: ((Cart) -> Unit)? = null
) :
    RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    fun setOnDeleteClickListener(listener: (Cart) -> Unit) {
        onDeleteClickListener = listener
    }

    fun setOnPlusClickListener(listener: (Cart) -> Unit) {
        onPlusClickLister = listener
    }

    fun setOnMinusClickListener(listener: (Cart) -> Unit) {
        onMinusClickLister = listener
    }

    fun setAddNote(listener: (Cart) -> Unit) {
        addNote = listener
    }


    inner class ViewHolder(private var binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindContent(cart: Cart) {
            binding.imageProduct.setImageResource(cart.photoMenu)
            binding.textNameProduct.text = cart.nameMenu
            binding.textPriceProduct.text = formatCurrency(cart.totalAmount)
            binding.textViewQuantity.text = cart.quantityMenu.toString()
            binding.btnDelete.setOnClickListener {
                onDeleteClickListener?.invoke(cart)
            }
            binding.btnPlus.setOnClickListener {
                onPlusClickLister?.invoke(cart)
            }
            binding.btnMinus.setOnClickListener {
                onMinusClickLister?.invoke(cart)
            }
            binding.edtNote.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun afterTextChanged(p0: Editable?) {
                    cart.note = p0.toString()
                    addNote?.invoke(cart)
                }

            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCartBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindContent(data[position])
    }

    fun incrementQuantity(position: Int) {
        val cartItem = data[position]
        cartItem.quantityMenu++
        notifyDataSetChanged()
    }

    fun decrementQuantity(position: Int) {
        val cartItem = data[position]
        if (cartItem.quantityMenu > 1) {
            cartItem.quantityMenu--
            notifyDataSetChanged()
        }
    }
}