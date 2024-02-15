package com.example.myapplication.presentaiton.bookitemview

import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.myapplication.R
import com.example.myapplication.databinding.GridBookItemBinding
import com.example.myapplication.model.response.rakutenbookdata.Item

class BookltemViewHolder(private var binding: GridBookItemBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Item) {
        val imgUri = item.Item.mediumImageUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(binding.itemImageView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_baseline_broken_image_24)
            )
            .into(binding.itemImageView)
        binding.itemTextView.text = item.Item.title
    }
}