package com.palmdev.domain.usecase.books

import com.palmdev.domain.model.BookReadingProgress
import com.palmdev.domain.repository.BooksContentRepository

class GetReadingProgressUseCase(private val booksContentRepository: BooksContentRepository) {

    fun execute(bookId: Int) : BookReadingProgress{
        return booksContentRepository.getBookReadingProgress(bookId)
    }
}