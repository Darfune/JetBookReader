package com.example.jetbookreader.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetbookreader.data.Resource
import com.example.jetbookreader.model.googleapimodel.Item
import com.example.jetbookreader.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsScreenViewModel @Inject constructor(private val repository: BookRepository) :
    ViewModel() {
        suspend fun getBookInfo(bookId: String): Resource<Item> {
            return repository.getBookInfo(bookId)
        }
}