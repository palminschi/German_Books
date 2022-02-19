package com.palmdev.domain.usecase.books

import com.palmdev.domain.repository.BooksRepository

class SetBookFavoriteStatusUseCase(private val booksRepository: BooksRepository) {

    fun execute(bookId: Int, status: Boolean){
        booksRepository.setFavoriteStatus(
            bookId = bookId,
            status = status
        )
    }

}