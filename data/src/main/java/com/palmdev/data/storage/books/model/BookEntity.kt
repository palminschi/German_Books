package com.palmdev.data.storage.books.model

data class BookEntity(
    val id: Int,
    val title: String,
    val author: String,
    val difficulty: String,
    val encodedImage: String,
    val favorite: Boolean = false,
    val premium: Boolean
)
