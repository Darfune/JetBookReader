package com.example.jetbookreader.screens.search

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetbookreader.data.Resource
import com.example.jetbookreader.model.googleapimodel.Item
import com.example.jetbookreader.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookSearchViewModel @Inject constructor(private val repository: BookRepository) :
    ViewModel() {
    var list: List<Item> by mutableStateOf(listOf())
    var isLoading: Boolean by mutableStateOf(true)

    init {
        loadBooks()
    }

    private fun loadBooks() {
        searchBooks("android")
    }

    fun searchBooks(query: String) {
        viewModelScope.launch(Dispatchers.Default) {
            if (query.isEmpty()) return@launch
            try {
                when (val response = repository.getBooks(query)) {
                    is Resource.Success -> {
                        list = response.data!!
                        isLoading = false
                    }
                    is Resource.Error -> {
                        Log.e("Network", "searchBooks: Failed getting books")
                        isLoading = false
                    }
                    else -> isLoading = false
                }
            } catch (exception: Exception) {
                Log.d("Network", "searchBooks: ${exception.message.toString()}")
            }
        }
    }
}