package com.palmdev.domain.repository

import com.palmdev.domain.model.Book

interface BooksRepository {

    fun getAllBooks(): List<Book>

    fun setFavoriteStatus(bookId: Int, status: Boolean)

    fun getLastBookRead(): Book

    fun saveLastBookRead(bookId: Int)

}