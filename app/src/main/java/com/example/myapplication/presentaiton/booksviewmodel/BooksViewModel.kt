package com.example.myapplication.presentaiton.booksviewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.RakutenApiClient
import com.example.myapplication.model.request.BooksAcquisitionRequest
import com.example.myapplication.model.response.RakutenBookResponse
import kotlinx.coroutines.launch

enum class RakutenApiStatus { LOADING, ERROR, DONE }
class BooksViewModel: ViewModel() {
    private val _status = MutableLiveData<RakutenApiStatus>()
    val status: LiveData<RakutenApiStatus> get() = _status
    private val _bookData = MutableLiveData<RakutenBookResponse>()
    val bookData: LiveData<RakutenBookResponse> get() = _bookData
    init {
        fetchPolularBooks()
    }
    fun fetchPolularBooks() {
        viewModelScope.launch {
            try {
                _status.value = RakutenApiStatus.LOADING
                RakutenApiClient.call(
                    BooksAcquisitionRequest(),
                    success = { rakutenBookResponse ->
                        Log.d("BooksViewModel", "Fetched popular books: $rakutenBookResponse")
                        _bookData.value = rakutenBookResponse
                        _status.value = RakutenApiStatus.DONE
                    },
                    failure = { errorMessage ->
                        Log.e("BooksViewModel", "Faild to fetch popular books: $errorMessage")
                    }
                )
            } catch (e: Exception) {
                Log.e("BoooksViewModel", "Error fetching popular books", e)
                _status.value = RakutenApiStatus.ERROR
            }
        }
    }
}