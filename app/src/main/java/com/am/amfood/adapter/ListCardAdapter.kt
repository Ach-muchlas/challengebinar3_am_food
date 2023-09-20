package com.am.amfood.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.am.amfood.R
import com.am.amfood.databinding.ContainerItemBinding
import com.am.amfood.model.CardModel
import com.am.amfood.ui.DetailActivity
import com.am.amfood.ui.detail.DetailFragment
import com.am.amfood.ui.home.HomeFragmentDirections

class ListCardAdapter(private val context: Context, private val listItem: ArrayList<CardModel>) :
    RecyclerView.Adapter<ListCardAdapter.ListViewHolder>() {

    class ListViewHolder(var binding: ContainerItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ContainerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listItem.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val card = listItem[position]
        holder.binding.textViewNameItem.text = card.name
        holder.binding.textViewRating.text = card.rate.toString()
        holder.binding.textViewPrice.text = card.price
        holder.binding.imageViewItem.setImageResource(card.photo)

        holder.itemView.setOnClickListener {

            val action = HomeFragmentDirections.actionNavigationHomeToDetailFragment(card)
            it.findNavController().navigate(action)
        }
    }
}