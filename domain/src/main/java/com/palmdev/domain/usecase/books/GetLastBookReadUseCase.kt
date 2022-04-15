package com.palmdev.domain.usecase.books

import com.palmdev.domain.model.Book
import com.palmdev.domain.repository.BooksRepository

class GetLastBookReadUseCase(private val booksRepository: BooksRepository) {

    fun execute(): Book? {
        return booksRepository.getLastBookRead()
    }

}