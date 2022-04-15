package com.palmdev.data.storage.books

import android.content.Context
import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import com.palmdev.data.storage.books.model.BookEntity
import com.palmdev.data.util.Base64Coder
import com.palmdev.data.util.Constants
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream


private const val EASY = "EASY"
private const val MEDIUM = "MEDIUM"
private const val HARD = "HARD"

class AllBooks(private val context: Context) {

    private val mSharedPrefs = context.getSharedPreferences(Constants.SHARED_PREFS_NAME, Context.MODE_PRIVATE)

    fun getBooks(): ArrayList<BookEntity> {
        val allBooks = ArrayList<BookEntity>()

        val book000 = BookEntity(
            id = 0,
            title = "Das Mädchen mit den Schwefelhölzern",
            author = "Hans Christian Andersen",
            difficulty = EASY,
            encodedImage = Base64Coder.encodeImageFromAssets(context,"book_images/img_book_000.jpg"),
            favorite = getBookFavoriteStatus(bookId = 0),
            premium = false
        )
        allBooks.add(book000)

        val book001 = BookEntity(
            id = 1,
            title = "Alice's Abenteuer im Wunderland",
            author = "Lewis Carroll",
            difficulty = MEDIUM,
            encodedImage = Base64Coder.encodeImageFromAssets(context,"book_images/img_book_001.jpg"),
            favorite = getBookFavoriteStatus(bookId = 1),
            premium = true
        )
        allBooks.add(book001)

        val book002 = BookEntity(
            id = 2,
            title = "Emil und die Detektive",
            author = "Erich Kästner",
            difficulty = MEDIUM,
            encodedImage = Base64Coder.encodeImageFromAssets(context,"book_images/img_book_002.jpg"),
            favorite = getBookFavoriteStatus(bookId = 2),
            premium = false
        )
        allBooks.add(book002)

        val book003 = BookEntity(
            id = 3,
            title = "Herr Vogel und Frau Wal",
            author = "TheFableCottage.com",
            difficulty = EASY,
            encodedImage = Base64Coder.encodeImageFromAssets(context, "book_images/img_book_003.jpg"),
            favorite = getBookFavoriteStatus(bookId = 3),
            premium = false
        )
        allBooks.add(book003)


        return allBooks
    }

    private fun getBookFavoriteStatus(bookId: Int): Boolean{
        return mSharedPrefs.getBoolean(Constants.FAVORITE_BOOK + bookId, false)
    }

}