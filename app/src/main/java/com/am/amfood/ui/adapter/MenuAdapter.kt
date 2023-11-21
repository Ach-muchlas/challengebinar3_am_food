package com.am.amfood.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.am.amfood.R
import com.am.amfood.data.lokal.entity.MenuEntity
import com.am.amfood.databinding.ContainerItemGridBinding
import com.am.amfood.databinding.ContainerItemLinearBinding
import com.am.amfood.ui.home.HomeFragmentDirections
import com.bumptech.glide.Glide

class MenuAdapter(
    private val isGrid: Boolean,
    private val onLikeClicked: (MenuEntity) -> Unit
) :
    ListAdapter<MenuEntity, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    inner class MenuLinearViewHolder(var binding: ContainerItemLinearBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindContentLinear(menu: MenuEntity) {
            Glide.with(binding.root.context).load(menu.imageUrl).into(binding.imageProductLinear)
            binding.tvNameProductLinear.text = menu.title
            binding.tvPriceProductLinear.text = menu.priceString
        }
    }

    inner class MenuGridViewHolder(var binding: ContainerItemGridBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindContentGrid(menu: MenuEntity) {
            Glide.with(binding.root.context).load(menu.imageUrl).into(binding.imageViewItem)
            binding.textViewNameItem.text = menu.title
            binding.textViewPrice.text = menu.priceString
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
            val menu = getItem(position)
            gridHolder.bindContentGrid(menu)
            gridHolder.itemView.setOnClickListener {
                val action =
                    HomeFragmentDirections.actionNavigationHomeToDetailFragment(getItem(position))
                it.findNavController().navigate(action)
            }
            val iconLike = gridHolder.binding.like

            if (menu.isLike) {
                iconLike.setImageDrawable(
                    ContextCompat.getDrawable(
                        iconLike.context,
                        R.drawable.hati
                    )
                )

            } else {
                iconLike.setImageDrawable(
                    ContextCompat.getDrawable(
                        iconLike.context,
                        R.drawable.love
                    )
                )
            }
            iconLike.setOnClickListener {
                onLikeClicked(menu)
            }
        } else {
            val linearHolder = holder as MenuLinearViewHolder
            val menu = getItem(position)
            linearHolder.bindContentLinear(menu)
            linearHolder.itemView.setOnClickListener {

                val action =
                    HomeFragmentDirections.actionNavigationHomeToDetailFragment(getItem(position))
                it.findNavController().navigate(action)
            }
            val iconLike = linearHolder.binding.like
            if (menu.isLike) {
                iconLike.setImageDrawable(
                    ContextCompat.getDrawable(
                        iconLike.context,
                        R.drawable.hati
                    )
                )
            } else {
                iconLike.setImageDrawable(
                    ContextCompat.getDrawable(
                        iconLike.context,
                        R.drawable.love
                    )
                )
            }
            iconLike.setOnClickListener {
                onLikeClicked(menu)
            }
        }
    }
    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MenuEntity>() {
            override fun areItemsTheSame(
                oldItem: MenuEntity,
                newItem: MenuEntity
            ): Boolean {
                return oldItem == newItem
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(
                oldItem: MenuEntity,
                newItem: MenuEntity
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}