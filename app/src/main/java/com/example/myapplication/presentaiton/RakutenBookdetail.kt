package com.example.myapplication.presentaiton

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentRakutenBookdetailBinding
import com.example.myapplication.databinding.FragmentRakutenbookactivityBinding
import com.example.myapplication.model.response.rakutenbookdata.Item


class RakutenBookdetail : Fragment() {
    private val args: RakutenBookdetailArgs by navArgs()
    private var _binding: FragmentRakutenBookdetailBinding? = null
    private val binding: FragmentRakutenBookdetailBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRakutenBookdetailBinding.bind(
            inflater.inflate(
                R.layout.fragment_rakuten_bookdetail,
                container,
                false
            )
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val item = args.item.Item
        val imgUrl = item.largeImageUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(binding.bookImage.context)
            .load(imgUrl)
            .also { requestOptions ->
                requestOptions.placeholder(R.drawable.loading_animation)
                requestOptions.error(R.drawable.ic_baseline_broken_image_24)
                requestOptions.fitCenter()
            }
            .into(binding.bookImage)
        binding.bookTitle.text = item.title
        binding.bookExplanation.text = item.itemCaption
        binding.bookExplanation.movementMethod = ScrollingMovementMethod.getInstance()
        binding.bookAuthor.text = item.author
        binding.bookPublisher.text = item.publisherName
    }
}