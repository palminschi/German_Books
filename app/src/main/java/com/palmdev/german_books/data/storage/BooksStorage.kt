package com.palmdev.german_books.data.storage

import android.content.Context
import javax.inject.Inject

private const val KEY_LAST_READ_BOOK_ID = "KEY_LAST_READ_BOOK_ID"

class BooksStorage @Inject constructor(context: Context) {

    private val prefs = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)

    fun saveLastReadBookId(id: Int) {
        prefs.edit().putInt(KEY_LAST_READ_BOOK_ID, id).apply()
    }

    val lastReadBookId : Int? get() {
        val id = prefs.getInt(KEY_LAST_READ_BOOK_ID, -1)
        return if (id == -1) null
        else id
    }
}