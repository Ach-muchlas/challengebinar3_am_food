package com.am.amfood.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.am.amfood.databinding.ContainerItemBinding
import com.am.amfood.model.CardModel
import com.am.amfood.ui.DetailActivity

class ListCardAdapter(private val context: Context, private val listItem: MutableList<CardModel>) :
    RecyclerView.Adapter<ListCardAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallBack: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallBack = onItemClickCallback
    }

    class ListViewHolder(var binding: ContainerItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ContainerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listItem.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (nameItem, ratingItem, priceItem, photoItem) = listItem[position]
        holder.binding.textViewNameItem.text = nameItem
        holder.binding.textViewRating.text = ratingItem
        holder.binding.textViewPrice.text = priceItem
        holder.binding.imageViewItem.setImageResource(photoItem)
        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            context.startActivity(intent)
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: CardModel)
    }
}