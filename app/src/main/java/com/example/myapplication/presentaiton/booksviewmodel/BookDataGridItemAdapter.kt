package com.example.myapplication.presentaiton.booksviewmodel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.example.myapplication.databinding.GridBookItemBinding
import com.example.myapplication.model.response.rakutenbookdata.Item
import com.example.myapplication.presentaiton.bookitemview.BookltemViewHolder
import androidx.recyclerview.widget.ListAdapter

class BookDataGridItemAdapter : ListAdapter<Item, BookltemViewHolder>(DiffCallback) {
    companion object DiffCallback : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.Item.title == newItem.Item.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookltemViewHolder {
        return BookltemViewHolder(GridBookItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: BookltemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}