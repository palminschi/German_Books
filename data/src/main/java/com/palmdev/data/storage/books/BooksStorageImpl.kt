package com.palmdev.data.storage.books

import android.content.Context
import com.palmdev.data.storage.books.model.BookEntity
import com.palmdev.data.util.Constants

class BooksStorageImpl(private val context: Context): BooksStorage {

    private val mSharedPrefs = context.getSharedPreferences(Constants.SHARED_PREFS_NAME, Context.MODE_PRIVATE)

    override fun getAllBooks(): List<BookEntity> {
        return AllBooks(context).getBooks()
    }

    override fun setFavoriteStatus(bookId: Int, status: Boolean) {
        mSharedPrefs.edit().putBoolean(Constants.FAVORITE_BOOK + bookId, status).apply()
    }

    override fun getLastBookRead(): BookEntity? {
        val bookId = mSharedPrefs.getInt(Constants.LAST_BOOK_READ, 999)
        if (bookId == 999) return null

        val allBooks = AllBooks(context).getBooks()
        var book: BookEntity? = null
        allBooks.forEach {
            if (it.id == bookId) book = it
        }
        return book
    }

    override fun saveLastBookRead(bookId: Int) {
        mSharedPrefs.edit().putInt(Constants.LAST_BOOK_READ, bookId).apply()
    }

    override fun getBookById(id: Int): BookEntity? {
        val allBooks = AllBooks(context).getBooks()
        var book: BookEntity? = null
        allBooks.forEach {
            if (it.id == id) book = it
        }
        return book
    }

}