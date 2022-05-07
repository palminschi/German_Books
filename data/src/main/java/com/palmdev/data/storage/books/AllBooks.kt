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

    private val mSharedPrefs =
        context.getSharedPreferences(Constants.SHARED_PREFS_NAME, Context.MODE_PRIVATE)

    fun getBooks(): ArrayList<BookEntity> {
        val allBooks = ArrayList<BookEntity>()

        val book000 = BookEntity(
            id = 0,
            title = "Das Mädchen mit den Schwefelhölzern",
            author = "Hans Christian Andersen",
            difficulty = EASY,
            encodedImage = Base64Coder.encodeImageFromAssets(
                context,
                "book_images/img_book_000.jpg"
            ),
            favorite = getBookFavoriteStatus(bookId = 0),
            premium = false
        )

        val book001 = BookEntity(
            id = 1,
            title = "Alice's Abenteuer im Wunderland",
            author = "Lewis Carroll",
            difficulty = MEDIUM,
            encodedImage = Base64Coder.encodeImageFromAssets(
                context,
                "book_images/img_book_001.jpg"
            ),
            favorite = getBookFavoriteStatus(bookId = 1),
            premium = true
        )

        val book002 = BookEntity(
            id = 2,
            title = "Emil und die Detektive",
            author = "Erich Kästner",
            difficulty = MEDIUM,
            encodedImage = Base64Coder.encodeImageFromAssets(
                context,
                "book_images/img_book_002.jpg"
            ),
            favorite = getBookFavoriteStatus(bookId = 2),
            premium = false
        )

        val book003 = BookEntity(
            id = 3,
            title = "Herr Vogel und Frau Wal",
            author = "TheFableCottage.com",
            difficulty = EASY,
            encodedImage = Base64Coder.encodeImageFromAssets(
                context,
                "book_images/img_book_003.jpg"
            ),
            favorite = getBookFavoriteStatus(bookId = 3),
            premium = false
        )

        val book004 = BookEntity(
            id = 4,
            title = "Die kleine Hexe",
            author = "Otfried Preußler",
            difficulty = EASY,
            encodedImage = Base64Coder.encodeImageFromAssets(
                context,
                "book_images/img_book_004.jpg"
            ),
            favorite = getBookFavoriteStatus(bookId = 4),
            premium = true
        )

        val book005 = BookEntity(
            id = 5,
            title = "Wo warst du, Adam?",
            author = "Heinrich Böll",
            difficulty = HARD,
            encodedImage = Base64Coder.encodeImageFromAssets(
                context,
                "book_images/img_book_005.jpg"
            ),
            favorite = getBookFavoriteStatus(bookId = 5),
            premium = false
        )

        val book006 = BookEntity(
            id = 6,
            title = "Krabat",
            author = "Otfried Preußler",
            difficulty = HARD,
            encodedImage = Base64Coder.encodeImageFromAssets(
                context,
                "book_images/img_book_006.jpg"
            ),
            favorite = getBookFavoriteStatus(bookId = 6),
            premium = true
        )

        val book007 = BookEntity(
            id = 7,
            title = "Der junge König",
            author = "Oscar Wilde",
            difficulty = EASY,
            encodedImage = Base64Coder.encodeImageFromAssets(
                context,
                "book_images/img_book_007.jpg"
            ),
            favorite = getBookFavoriteStatus(bookId = 7),
            premium = false
        )

        val book008 = BookEntity(
            id = 8,
            title = "Kaltes Blut",
            author = "Roland Dittrich",
            difficulty = EASY,
            encodedImage = Base64Coder.encodeImageFromAssets(
                context,
                "book_images/img_book_008.jpg"
            ),
            favorite = getBookFavoriteStatus(bookId = 8),
            premium = false
        )

        val book009 = BookEntity(
            id = 9,
            title = "Anna, Berlin",
            author = "Thomas Silvin",
            difficulty = EASY,
            encodedImage = Base64Coder.encodeImageFromAssets(
                context,
                "book_images/img_book_009.jpg"
            ),
            favorite = getBookFavoriteStatus(bookId = 9),
            premium = false
        )

        val book010 = BookEntity(
            id = 10,
            title = "Der Schnee",
            author = "Sophie Reinheimer",
            difficulty = EASY,
            encodedImage = Base64Coder.encodeImageFromAssets(
                context,
                "book_images/img_book_010.jpg"
            ),
            favorite = getBookFavoriteStatus(bookId = 10),
            premium = true
        )

        val book011 = BookEntity(
            id = 11,
            title = "Das kalte Herz",
            author = "Wilhelm Hauff",
            difficulty = MEDIUM,
            encodedImage = Base64Coder.encodeImageFromAssets(
                context,
                "book_images/img_book_011.jpg"
            ),
            favorite = getBookFavoriteStatus(bookId = 11),
            premium = false
        )

        val book012 = BookEntity(
            id = 12,
            title = "Vom Fischer und seiner Frau",
            author = "Philipp Otto Runge",
            difficulty = MEDIUM,
            encodedImage = Base64Coder.encodeImageFromAssets(
                context,
                "book_images/img_book_012.jpg"
            ),
            favorite = getBookFavoriteStatus(bookId = 12),
            premium = false
        )

        val book013 = BookEntity(
            id = 13,
            title = "Aladdin und die Wunderlampe",
            author = "Ludwig Fulda",
            difficulty = MEDIUM,
            encodedImage = Base64Coder.encodeImageFromAssets(
                context,
                "book_images/img_book_013.jpg"
            ),
            favorite = getBookFavoriteStatus(bookId = 13),
            premium = true
        )

        val book014 = BookEntity(
            id = 14,
            title = "Heidi kann brauchen, was es gelernt hat",
            author = "Johanna Spyri",
            difficulty = HARD,
            encodedImage = Base64Coder.encodeImageFromAssets(
                context,
                "book_images/img_book_014.jpg"
            ),
            favorite = getBookFavoriteStatus(bookId = 14),
            premium = true
        )

        val book015 = BookEntity(
            id = 15,
            title = "Die Biene Maja und ihre Abenteuer",
            author = "Waldemar Bonsels",
            difficulty = MEDIUM,
            encodedImage = Base64Coder.encodeImageFromAssets(
                context,
                "book_images/img_book_015.jpg"
            ),
            favorite = getBookFavoriteStatus(bookId = 15),
            premium = true
        )

        val book016 = BookEntity(
            id = 16,
            title = "Peterchens Mondfahrt",
            author = "Gerdt von Bassewitz",
            difficulty = EASY,
            encodedImage = Base64Coder.encodeImageFromAssets(
                context,
                "book_images/img_book_016.jpg"
            ),
            favorite = getBookFavoriteStatus(bookId = 16),
            premium = false
        )

        allBooks.add(book000)
        allBooks.add(book008)
        allBooks.add(book009)
        allBooks.add(book001)
        allBooks.add(book002)
        allBooks.add(book003)
        allBooks.add(book004)
        allBooks.add(book005)
        allBooks.add(book006)
        allBooks.add(book007)
        allBooks.add(book010)
        allBooks.add(book011)
        allBooks.add(book012)
        allBooks.add(book013)
        allBooks.add(book014)
        allBooks.add(book015)
        allBooks.add(book016)

        return allBooks
    }

    private fun getBookFavoriteStatus(bookId: Int): Boolean {
        return mSharedPrefs.getBoolean(Constants.FAVORITE_BOOK + bookId, false)
    }

}