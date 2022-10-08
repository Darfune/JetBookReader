package com.example.jetbookreader.repository

import com.example.jetbookreader.data.DataOrException
import com.example.jetbookreader.model.googleapimodel.Item
import com.example.jetbookreader.network.BooksApi
import javax.inject.Inject

class BookRepository @Inject constructor(private val api: BooksApi) {
    private val dataOrException = DataOrException<List<Item>, Boolean, Exception>()
    private val bookInfoDataException = DataOrException<Item, Boolean, Exception>()

    suspend fun getBooks(searchQuery: String): DataOrException<List<Item>, Boolean, Exception> {
        try {
            dataOrException.loading = true
            dataOrException.data = api.getAllBooks(searchQuery).items
            if (dataOrException.data!!.isNotEmpty()) dataOrException.loading = false
        } catch (e: Exception) {
            dataOrException.e = e
        }
        return dataOrException
    }

    suspend fun getBookInfo(bookId: String): DataOrException<Item, Boolean, Exception> {
        val response = try {
            dataOrException.loading = true
            bookInfoDataException.data = api.getBookInfo(bookId = bookId)
            if (bookInfoDataException.data.toString().isNotEmpty()) bookInfoDataException.loading = false else {}
        } catch (e: Exception) {
            bookInfoDataException.e = e
        }
        return bookInfoDataException
    }
}