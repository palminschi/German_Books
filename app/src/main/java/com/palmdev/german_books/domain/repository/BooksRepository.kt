package com.palmdev.german_books.domain.repository

import com.palmdev.german_books.domain.model.Book
import com.palmdev.german_books.domain.model.ReadBook

interface BooksRepository {

    suspend fun getAllBooks(): List<Book>
    suspend fun getBookById(id: Int): Book?
    suspend fun getBooksByLevel(level: String): List<Book>
    suspend fun getAllReadBooks(): List<ReadBook>
    suspend fun getFavoriteBooks(): List<ReadBook>
    suspend fun getLastReadBook(): ReadBook?
    suspend fun getReadBookById(id: Int): ReadBook?
    suspend fun saveBook(readBook: ReadBook)
    suspend fun setBookIsFavorite(isFavorite: Boolean, book: ReadBook)
    suspend fun setLastReadBookId(id: Int)
}