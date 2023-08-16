package com.palmdev.german_books.data.repository

import android.util.Log
import com.palmdev.german_books.data.database.BooksDao
import com.palmdev.german_books.data.database.ReadBooksDao
import com.palmdev.german_books.data.storage.BooksStorage
import com.palmdev.german_books.domain.model.Book
import com.palmdev.german_books.domain.model.ReadBook
import com.palmdev.german_books.domain.repository.BooksRepository
import com.palmdev.german_books.utils.CAUGHT_ERROR
import javax.inject.Inject

class BooksRepositoryImpl @Inject constructor(
    private val booksDao: BooksDao,
    private val readBooksDao: ReadBooksDao,
    private val booksStorage: BooksStorage
) : BooksRepository {

    override suspend fun getAllBooks(): List<Book> {
        return try {
            booksDao.getAllBooks()
        } catch (e: Exception) {
            e.message?.let { Log.e(CAUGHT_ERROR, it) }
            emptyList()
        }
    }

    override suspend fun getBookById(id: Int): Book? {
        return booksDao.getBookById(id)
    }

    override suspend fun getBooksByLevel(level: String): List<Book> {
        return try {
            booksDao.getBooksByLevel(level)
        } catch (e: Exception) {
            e.message?.let { Log.e(CAUGHT_ERROR, it) }
            emptyList()
        }
    }

    override suspend fun getAllReadBooks(): List<ReadBook> {
        return try {
            readBooksDao.getAll()
        } catch (e: Exception) {
            e.message?.let { Log.e(CAUGHT_ERROR, it) }
            emptyList()
        }
    }

    override suspend fun getFavoriteBooks(): List<ReadBook> {
        return try {
            readBooksDao.getFavoriteBooks()
        } catch (e: Exception) {
            e.message?.let { Log.e(CAUGHT_ERROR, it) }
            emptyList()
        }
    }

    override suspend fun getLastReadBook(): ReadBook? {
        val id = booksStorage.lastReadBookId ?: return null
        return readBooksDao.getBookById(id)
    }

    override suspend fun setLastReadBookId(id: Int) {
        booksStorage.saveLastReadBookId(id)
    }

    override suspend fun getReadBookById(id: Int): ReadBook? {
        return try {
            readBooksDao.getBookById(id)
        } catch (e: Exception) {
            e.message?.let { Log.e(CAUGHT_ERROR, it) }
            null
        }
    }

    override suspend fun saveBook(readBook: ReadBook) {
        readBooksDao.insert(readBook)
    }

    override suspend fun setBookIsFavorite(isFavorite: Boolean, book: ReadBook) {
        readBooksDao.insert(
            book.copy(
                isFavorite = isFavorite
            )
        )
    }
}