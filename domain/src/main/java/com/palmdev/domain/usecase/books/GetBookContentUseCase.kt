package com.palmdev.domain.usecase.books

import com.palmdev.domain.model.BookContent
import com.palmdev.domain.repository.BooksContentRepository

class GetBookContentUseCase(private val booksContentRepository: BooksContentRepository) {

    fun execute(bookId: Int) : BookContent{
        return booksContentRepository.getBookContentById(bookId)
    }

}