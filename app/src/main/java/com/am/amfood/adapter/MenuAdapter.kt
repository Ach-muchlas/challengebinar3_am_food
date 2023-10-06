package com.am.amfood.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.am.amfood.databinding.ContainerItemGridBinding
import com.am.amfood.databinding.ContainerItemLinearBinding
import com.am.amfood.model.Product
import com.am.amfood.ui.home.HomeFragmentDirections
import com.am.amfood.utils.Utils.formatCurrency

class MenuAdapter(
    private val data: ArrayList<Product>,
    private val isGrid: Boolean
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class MenuLinearViewHolder(private var binding: ContainerItemLinearBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindContentLinear(cardModel: Product) {
            binding.imageProductLinear.setImageResource(cardModel.photo)
            binding.tvNameProductLinear.text = cardModel.name
            binding.tvRateProductLinear.text = cardModel.rate.toString()
            binding.tvPriceProductLinear.text = formatCurrency(cardModel.price)
        }
    }

    inner class MenuGridViewHolder(private var binding: ContainerItemGridBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindContentGrid(cardModel: Product) {
            binding.imageViewItem.setImageResource(cardModel.photo)
            binding.textViewNameItem.text = cardModel.name
            binding.textViewRating.text = cardModel.rate.toString()
            binding.textViewPrice.text = formatCurrency(cardModel.price)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (isGrid) {
            MenuGridViewHolder(
                ContainerItemGridBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else {
            MenuLinearViewHolder(
                ContainerItemLinearBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (isGrid) {
            val gridHolder = holder as MenuGridViewHolder
            gridHolder.bindContentGrid(data[position])
            gridHolder.itemView.setOnClickListener {
                val action =
                    HomeFragmentDirections.actionNavigationHomeToDetailFragment(data[position])
                it.findNavController().navigate(action)
            }
        } else{
            val linearHolder = holder as MenuLinearViewHolder
            linearHolder.bindContentLinear(data[position])
            linearHolder.itemView.setOnClickListener {
                val action =
                    HomeFragmentDirections.actionNavigationHomeToDetailFragment(data[position])
                it.findNavController().navigate(action)
            }
        }
    }
}