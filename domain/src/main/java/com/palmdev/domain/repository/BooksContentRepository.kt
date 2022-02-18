package com.palmdev.domain.repository

import com.palmdev.domain.model.BookContent
import com.palmdev.domain.model.BookReadingProgress

interface BooksContentRepository {

    fun getBookContentById(id: Int): BookContent

    fun saveBookReadingProgress(readingProgress: BookReadingProgress)

    fun getBookReadingProgress(bookId: Int): BookReadingProgress

}