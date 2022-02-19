package com.palmdev.domain.usecase.books

import com.palmdev.domain.repository.BooksRepository

class SaveLastBookReadUseCase(private val booksRepository: BooksRepository) {

    fun execute(bookId: Int){
        booksRepository.saveLastBookRead(bookId = bookId)
    }

}