package com.example.myapplication.presentaiton

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentRakutenbookactivityBinding
import com.example.myapplication.model.response.RakutenBookResponse
import com.example.myapplication.model.response.rakutenbookdata.Item
import com.example.myapplication.presentaiton.booksviewmodel.BookDataGridItemAdapter
import com.example.myapplication.presentaiton.booksviewmodel.BooksViewModel

class RakutenBookActivity : Fragment() {
    private var _binding: FragmentRakutenbookactivityBinding? = null
    private val binding: FragmentRakutenbookactivityBinding get() = _binding!!
    private val viewModel: BooksViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRakutenbookactivityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        val recyclerView = binding.bookItemGridView
        val itemAdapter = BookDataGridItemAdapter { item ->
                goToItemRditScreen(item)
        }
        recyclerView.adapter = itemAdapter

        viewModel.bookData.observe(viewLifecycleOwner, Observer<RakutenBookResponse> {
            val items = mutableListOf<Item>()
            val res = it.Items.iterator()
            for (item in res) {
                items.add(item)
            }
            itemAdapter.submitList(items)
        })
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.fetchPolularBooks()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search, menu)
    }
    private fun goToItemRditScreen(item: Item) {
        //データを渡す時後で引数を入れる
        val action = RakutenBookActivityDirections.actionFragmentRakutenbookactivityToFragmentrakutenbookdetail(item)
        findNavController().navigate(action)
        requireActivity().title = getString((R.string.book_detail))
    }

    override fun onResume() {
        super.onResume()
        requireActivity().title = getString((R.string.app_name))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}