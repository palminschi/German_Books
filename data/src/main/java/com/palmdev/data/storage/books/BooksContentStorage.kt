package com.palmdev.data.storage.books

import com.palmdev.data.storage.books.model.BookContentEntity
import com.palmdev.data.storage.books.model.BookReadingProgressEntity

interface BooksContentStorage {

    fun getBookContentById(bookId: Int): BookContentEntity

    fun saveReadingProgress(readingProgress: BookReadingProgressEntity)

    fun getReadingProgress(bookId: Int): BookReadingProgressEntity

}