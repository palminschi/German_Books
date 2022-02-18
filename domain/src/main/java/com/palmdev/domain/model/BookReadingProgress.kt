package com.palmdev.domain.model

data class BookReadingProgress(
    val bookId: Int,
    val currentPage: Int = 0,
    val totalPages: Int
)
