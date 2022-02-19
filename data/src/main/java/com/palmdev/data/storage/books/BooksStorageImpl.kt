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

    override fun getLastBookRead(): BookEntity {
        val bookId = mSharedPrefs.getInt(Constants.LAST_BOOK_READ, 0)
        return AllBooks(context).getBooks().get(index = bookId)
    }

    override fun saveLastBookRead(bookId: Int) {
        mSharedPrefs.edit().putInt(Constants.LAST_BOOK_READ, bookId).apply()
    }

}