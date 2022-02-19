package com.palmdev.data.storage.books

import com.palmdev.data.storage.books.model.BookEntity

interface BooksStorage {

    fun getAllBooks(): List<BookEntity>

    fun setFavoriteStatus(bookId: Int, status: Boolean)

    fun getLastBookRead(): BookEntity

    fun saveLastBookRead(bookId: Int)

}