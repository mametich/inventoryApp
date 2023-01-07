package com.example.inventory.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.inventory.data.Item
import com.example.inventory.data.getFormattedPrice
import com.example.inventory.databinding.ItemListItemBinding

class ItemListAdapter(private val listener: Navigator)
    : ListAdapter<Item, ItemListAdapter.ItemViewHolder>(DiffCallBack), View.OnClickListener {

    override fun onClick(v: View?) {
        val item = v?.tag as Item
        listener.onChooseItem(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemListItemBinding.inflate(inflater, parent,false)

        binding.root.setOnClickListener(this)

        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)

        holder.apply {
            binding.root.tag = item
            bind(item)
        }
    }

    class ItemViewHolder(val binding: ItemListItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item){
            binding.apply {
                itemName.text = item.itemName
                itemPrice.text = item.getFormattedPrice()
                itemQuantity.text = item.quantityInStock.toString()
            }
        }
    }

    companion object {
        private val DiffCallBack = object : DiffUtil.ItemCallback<Item>(){
            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem.itemName == newItem.itemName
            }
        }
    }
}