package com.palmdev.data.storage.books

import android.content.Context
import android.content.res.AssetManager
import com.palmdev.data.storage.books.model.BookContentEntity
import com.palmdev.data.storage.books.model.BookReadingProgressEntity
import com.palmdev.data.util.Constants
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class AssetsBooksContentStorage(context: Context) : BooksContentStorage {

    private val mAssets: AssetManager = context.assets
    private val mSharedPrefs = context.getSharedPreferences(Constants.SHARED_PREFS_NAME, Context.MODE_PRIVATE)


    override fun getBookContentById(bookId: Int): BookContentEntity {
        val fileName = when(bookId){
            0 -> "books/b_001_the_cat.txt"
            1 -> "books/b_001_the_cat.txt"
            else -> { "books/b_001_the_cat.txt" }
        }

        val bookContent = getStringFromAssets(fileName = fileName)

        return BookContentEntity(id = bookId, content = bookContent)
    }

    override fun saveReadingProgress(readingProgress: BookReadingProgressEntity){
        // Save Current Page
        mSharedPrefs.edit().putInt(
            Constants.BOOK_CURRENT_PAGE + readingProgress.bookId,
            readingProgress.currentPage
        ).apply()
        // Save Total Pages
        mSharedPrefs.edit().putInt(
            Constants.BOOK_TOTAL_PAGES + readingProgress.bookId,
            readingProgress.totalPages
        ).apply()
    }

    override fun getReadingProgress(bookId: Int): BookReadingProgressEntity {
        val currentPage = mSharedPrefs.getInt(Constants.BOOK_CURRENT_PAGE + bookId, 0)
        val totalPages = mSharedPrefs.getInt(Constants.BOOK_TOTAL_PAGES + bookId, 100)

        return BookReadingProgressEntity(
            bookId = bookId,
            currentPage = currentPage,
            totalPages = totalPages
        )
    }



    private fun getStringFromAssets(fileName: String): String {
        val stringBuilder = StringBuilder()
        var reader: BufferedReader? = null
        try {
            reader = BufferedReader(InputStreamReader(mAssets.open(fileName)))
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                stringBuilder.append(line)
                stringBuilder.append('\n')
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (reader != null){
                try {
                    reader.close()
                }catch (e: IOException){
                    e.printStackTrace()
                }
            }
        }

        return stringBuilder.toString()
    }

}