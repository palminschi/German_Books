package com.palmdev.data.storage.books.model

data class BookReadingProgressEntity(
    val bookId: Int,
    val currentPage: Int = 0,
    val totalPages: Int
)
