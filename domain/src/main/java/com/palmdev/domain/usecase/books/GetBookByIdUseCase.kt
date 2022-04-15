package com.palmdev.domain.usecase.books

import com.palmdev.domain.model.Book
import com.palmdev.domain.repository.BooksRepository

class GetBookByIdUseCase (private val booksRepository: BooksRepository) {

    fun execute(id: Int): Book? {
        return booksRepository.getBookById(id)
    }
}