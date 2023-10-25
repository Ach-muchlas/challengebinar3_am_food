package com.am.amfood.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.am.amfood.databinding.ContainerItemGridBinding
import com.am.amfood.databinding.ContainerItemLinearBinding
import com.am.amfood.data.remote.response.DataItem
import com.am.amfood.ui.home.HomeFragmentDirections
import com.bumptech.glide.Glide

class MenuAdapter(
    private val isGrid: Boolean
) :
    ListAdapter<DataItem, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    inner class MenuLinearViewHolder(private var binding: ContainerItemLinearBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindContentLinear(menu: DataItem) {
            Glide.with(binding.root.context).load(menu.imageUrl).into(binding.imageProductLinear)
            binding.tvNameProductLinear.text = menu.nama
            binding.tvPriceProductLinear.text = menu.hargaFormat
        }
    }

    inner class MenuGridViewHolder(private var binding: ContainerItemGridBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindContentGrid(menu: DataItem) {
            Glide.with(binding.root.context).load(menu.imageUrl).into(binding.imageViewItem)
            binding.textViewNameItem.text = menu.nama
            binding.textViewPrice.text = menu.hargaFormat
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

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (isGrid) {
            val gridHolder = holder as MenuGridViewHolder
            gridHolder.bindContentGrid(getItem(position))
            gridHolder.itemView.setOnClickListener {
                val action =
                    HomeFragmentDirections.actionNavigationHomeToDetailFragment(getItem(position))
                it.findNavController().navigate(action)
            }
        } else{
            val linearHolder = holder as MenuLinearViewHolder
            linearHolder.bindContentLinear(getItem(position))
            linearHolder.itemView.setOnClickListener {
                val action =
                    HomeFragmentDirections.actionNavigationHomeToDetailFragment(getItem(position))
                it.findNavController().navigate(action)
            }
        }
    }
    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItem>() {
            override fun areItemsTheSame(
                oldItem: DataItem,
                newItem: DataItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: DataItem,
                newItem: DataItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}