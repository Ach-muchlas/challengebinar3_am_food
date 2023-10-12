package com.am.amfood.ui.adapter

import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.am.amfood.databinding.ItemCartBinding
import com.am.amfood.data.lokal.entity.Cart
import com.am.amfood.utils.Utils.formatCurrency

class CartAdapter(
    private val data: List<Cart>,
    private var onDeleteClickListener: ((Cart) -> Unit)? = null,
    private var onPlusClickLister: ((Cart) -> Unit)? = null,
    private var onMinusClickLister: ((Cart) -> Unit)? = null,
    private var addNote: ((Cart) -> Unit)? = null,
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
            val edt = binding.edtNote
            edt.setOnKeyListener { _, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    val newText = edt.text.toString().trim()
                    if (newText.isNotEmpty()) {
                        cart.note = newText
                        addNote?.invoke(cart)
                    }
                    true
                } else {
                    false
                }
            }
            edt.setText(cart.note)
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

}