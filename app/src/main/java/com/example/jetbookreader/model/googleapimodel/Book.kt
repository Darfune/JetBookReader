package com.example.jetbookreader.model.googleapimodel

data class Book(
    val items: List<Item>,
    val kind: String,
    val totalItems: Int
)