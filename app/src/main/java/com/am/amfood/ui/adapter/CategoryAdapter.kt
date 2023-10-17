package com.am.amfood.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.am.amfood.data.remote.response.DataItemCategory
import com.am.amfood.databinding.CategoryMenuBinding
import com.bumptech.glide.Glide

class CategoryAdapter : ListAdapter<DataItemCategory, CategoryAdapter.MyViewHolder>(DIFF_CALLBACK) {
    inner class MyViewHolder(private val binding: CategoryMenuBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(menu: DataItemCategory) {
            binding.textViewNameItem.text = menu.nama
            Glide.with(binding.root.context).load(menu.imageUrl).into(binding.imageViewItem)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            CategoryMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val dataCategory = getItem(position)
        holder.bind(dataCategory)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItemCategory>() {
            override fun areItemsTheSame(
                oldItem: DataItemCategory,
                newItem: DataItemCategory
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: DataItemCategory,
                newItem: DataItemCategory
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}