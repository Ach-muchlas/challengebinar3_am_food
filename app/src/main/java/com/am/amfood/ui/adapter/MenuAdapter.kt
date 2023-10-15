package com.am.amfood.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.am.amfood.databinding.ContainerItemGridBinding
import com.am.amfood.databinding.ContainerItemLinearBinding
import com.am.amfood.data.remote.response.DataItem
import com.am.amfood.ui.home.HomeFragmentDirections

class MenuAdapter(
    private val data: List<DataItem>,
    private val isGrid: Boolean
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class MenuLinearViewHolder(private var binding: ContainerItemLinearBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindContentLinear(cardModel: DataItem) {
            binding.tvNameProductLinear.text = cardModel.nama
            binding.tvPriceProductLinear.text = cardModel.hargaFormat
        }
    }

    inner class MenuGridViewHolder(private var binding: ContainerItemGridBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindContentGrid(cardModel: DataItem) {
            binding.textViewNameItem.text = cardModel.nama
            binding.textViewPrice.text = cardModel.hargaFormat
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