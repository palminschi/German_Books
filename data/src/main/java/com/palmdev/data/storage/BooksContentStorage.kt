package com.palmdev.data.storage

import com.palmdev.data.storage.model.BookContentEntity

interface BooksContentStorage {

    fun getBookContentById(bookId: Int): BookContentEntity

    fun saveReadingProgress(bookId: Int, readingProgress: Int)

}