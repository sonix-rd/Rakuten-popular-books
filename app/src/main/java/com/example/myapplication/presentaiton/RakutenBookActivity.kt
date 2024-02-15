package com.example.myapplication.presentaiton

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.databinding.FragmentRakutenbookactivityBinding
import com.example.myapplication.model.response.RakutenBookResponse
import com.example.myapplication.model.response.rakutenbookdata.Item
import com.example.myapplication.presentaiton.booksviewmodel.BookDataGridItemAdapter
import com.example.myapplication.presentaiton.booksviewmodel.BooksViewModel

class RakutenBookActivity : Fragment() {
    private var _binding: FragmentRakutenbookactivityBinding? = null
    private val binding: FragmentRakutenbookactivityBinding get() = _binding!!
    private lateinit var viewModel: BooksViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRakutenbookactivityBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ViewModelの初期化
        viewModel = ViewModelProvider(this).get(BooksViewModel::class.java)

        val recyclerView = binding.bookItemGridView
        val itemAdapter = BookDataGridItemAdapter()
        recyclerView.adapter = itemAdapter

        viewModel.bookData.observe( viewLifecycleOwner, Observer<RakutenBookResponse> {
            val items = mutableListOf<Item>()
            val res = it.Items.iterator()
            for (item in res) {
                items.add(item)
            }
            itemAdapter.submitList(items)
        })
    }
}