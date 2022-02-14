package com.palmdev.domain.repository

import com.palmdev.domain.model.BookContent

interface BooksContentRepository {

    fun getBookContentById(id: Int): BookContent

    fun saveBookReadingProgress(bookId: Int, readingProgress: Int)

}