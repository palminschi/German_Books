package com.palmdev.domain.usecase.books

import com.palmdev.domain.model.BookReadingProgress
import com.palmdev.domain.repository.BooksContentRepository

class SaveReadingProgressUseCase(private val booksContentRepository: BooksContentRepository) {

    fun execute(readingProgress: BookReadingProgress){
        booksContentRepository.saveBookReadingProgress(readingProgress = readingProgress)
    }

}