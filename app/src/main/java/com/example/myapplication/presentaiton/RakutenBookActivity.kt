package com.example.myapplication.presentaiton

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
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
        binding.swipeRefreshLayout.isRefreshing = true
        viewModel.bookData.observe(viewLifecycleOwner, Observer<RakutenBookResponse> {
            val items = mutableListOf<Item>()
            val res = it.Items.iterator()
            for (item in res) {
                items.add(item)
            }
            itemAdapter.submitList(items)
            binding.swipeRefreshLayout.isRefreshing = false
        })
        binding.swipeRefreshLayout.setOnRefreshListener {
            //初回の再読み込み時
            viewModel.setReturningFromDetail(false)
            viewModel.fetchPolularBooks()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.queryHint = getString(R.string.search_hint)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                //検索ボタンを押した時
                viewModel.fetchPolularBooks(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // テキストが変更されたときの処理
                newText?.let { viewModel.fetchPolularBooks(it) }
                return true
            }
        })
    }

    private fun goToItemRditScreen(item: Item) {
        val action =
            RakutenBookActivityDirections.actionFragmentRakutenbookactivityToFragmentrakutenbookdetail(
                item
            )
        findNavController().navigate(action)
        requireActivity().title = getString((R.string.book_detail))
    }

    override fun onResume() {
        super.onResume()
        requireActivity().title = getString((R.string.app_name))
        // 詳細画面から戻ってきた場合はフラグをセット
        viewModel.setReturningFromDetail(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}