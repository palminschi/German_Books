package com.palmdev.domain.usecase.books

import com.palmdev.domain.model.Book
import com.palmdev.domain.model.BooksType
import com.palmdev.domain.repository.BooksRepository

class GetBooksByTypeUseCase(private val booksRepository: BooksRepository) {

    fun execute(type: BooksType): List<Book>{

        val allBooks = booksRepository.getAllBooks()

        return when (type) {
            BooksType.ALL -> allBooks

            BooksType.EASY -> {
                val easyBooks = ArrayList<Book>()
                allBooks.forEach {
                    if (it.difficulty == "EASY") easyBooks.add(it)
                }
                return easyBooks
            }

            BooksType.MEDIUM -> {
                val mediumBooks = ArrayList<Book>()
                allBooks.forEach {
                    if (it.difficulty == "MEDIUM") mediumBooks.add(it)
                }
                return mediumBooks
            }

            BooksType.HARD -> {
                val hardBooks = ArrayList<Book>()
                allBooks.forEach {
                    if (it.difficulty == "HARD") hardBooks.add(it)
                }
                return hardBooks
            }

            BooksType.FAVORITES -> {
                val favoriteBooks = ArrayList<Book>()
                allBooks.forEach {
                    if (it.favorite) favoriteBooks.add(it)
                }
                return favoriteBooks
            }
        }

    }
}