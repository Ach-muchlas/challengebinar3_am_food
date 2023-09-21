package com.am.amfood.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.am.amfood.databinding.ContainerItemGridBinding
import com.am.amfood.databinding.ContainerItemLinearBinding
import com.am.amfood.model.CardModel
import com.am.amfood.ui.home.HomeFragmentDirections

class MenuAdapter(private val data: ArrayList<CardModel>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var isLinear = false

    companion object {
        private const val VIEW_TYPE_GRID = 1
        private const val VIEW_TYPE_LINEAR = 2
    }

    inner class MenuLinearViewHolder(var binding: ContainerItemLinearBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindContentLinear(cardModel: CardModel) {
            binding.imageProductLinear.setImageResource(cardModel.photo)
            binding.tvNameProductLinear.text = cardModel.name
            binding.tvRateProductLinear.text = cardModel.rate.toString()
            binding.tvPriceProductLinear.text = cardModel.price
        }
    }

    inner class MenuGridViewHolder(var binding: ContainerItemGridBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindContentGrid(cardModel: CardModel) {
            binding.imageViewItem.setImageResource(cardModel.photo)
            binding.textViewNameItem.text = cardModel.name
            binding.textViewRating.text = cardModel.rate.toString()
            binding.textViewPrice.text = cardModel.price
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_GRID -> {
                MenuGridViewHolder(
                    ContainerItemGridBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            VIEW_TYPE_LINEAR -> {
                MenuLinearViewHolder(
                    ContainerItemLinearBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            else -> {
                throw IllegalArgumentException("Invalid view type")
            }
        }

    }

    override fun getItemCount(): Int = data.size

    override fun getItemViewType(position: Int): Int {
        return if (isLinear) VIEW_TYPE_LINEAR else VIEW_TYPE_GRID
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        return when (holder.itemViewType) {
            VIEW_TYPE_GRID -> {
                val gridHolder = holder as MenuGridViewHolder
                gridHolder.bindContentGrid(data[position])
                gridHolder.itemView.setOnClickListener {
                    val action =
                        HomeFragmentDirections.actionNavigationHomeToDetailFragment(data[position])
                    it.findNavController().navigate(action)
                }
            }

            VIEW_TYPE_LINEAR -> {
                val linearHolder = holder as MenuLinearViewHolder
                linearHolder.bindContentLinear(data[position])
                linearHolder.itemView.setOnClickListener {
                    val action =
                        HomeFragmentDirections.actionNavigationHomeToDetailFragment(data[position])
                    it.findNavController().navigate(action)
                }
            }

            else -> throw IllegalArgumentException("Undefined view type")
        }

    }


    /*function to convert layout manager*/
    @SuppressLint("NotifyDataSetChanged")
    fun toggleView(recyclerView: RecyclerView) {
        isLinear = !isLinear
        notifyDataSetChanged()

        if (isLinear) {
            recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
        } else {
            recyclerView.layoutManager = GridLayoutManager(recyclerView.context, 2)
        }
    }
}