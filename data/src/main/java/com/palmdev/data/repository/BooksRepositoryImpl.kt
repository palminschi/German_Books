package com.palmdev.data.repository

import com.palmdev.data.storage.books.BooksStorage
import com.palmdev.data.storage.books.model.BookEntity
import com.palmdev.domain.model.Book
import com.palmdev.domain.repository.BooksRepository

class BooksRepositoryImpl(private val booksStorage: BooksStorage) : BooksRepository {

    override fun getAllBooks(): List<Book> {
        return mapListOfBooksToDomain(
            listOfBookEntity = booksStorage.getAllBooks()
        )
    }

    override fun setFavoriteStatus(bookId: Int, status: Boolean) {
        booksStorage.setFavoriteStatus(bookId = bookId, status = status)
    }

    override fun getLastBookRead(): Book? {
        return mapBookToDomain(
            bookEntity = booksStorage.getLastBookRead()
        )
    }

    override fun saveLastBookRead(bookId: Int) {
        booksStorage.saveLastBookRead(bookId = bookId)
    }

    override fun getBookById(id: Int): Book? {
        return mapBookToDomain(bookEntity = booksStorage.getBookById(id))
    }


    // Mappers
    private fun mapBookToDomain(bookEntity: BookEntity?): Book? {
        if (bookEntity == null) return null
        return Book(
            id = bookEntity.id,
            title = bookEntity.title,
            author = bookEntity.author,
            difficulty = bookEntity.difficulty,
            encodedImage = bookEntity.encodedImage,
            favorite = bookEntity.favorite,
            isPremium = bookEntity.premium
        )
    }

    private fun mapListOfBooksToDomain(listOfBookEntity: List<BookEntity>):List<Book> {

        val listOfBooks = ArrayList<Book>()

        listOfBookEntity.forEach {
            mapBookToDomain(it)?.let { book -> listOfBooks.add(book) }
        }
        return listOfBooks
    }

}