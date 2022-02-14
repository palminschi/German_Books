package com.palmdev.data.storage

import android.content.Context
import android.content.res.AssetManager
import com.palmdev.data.storage.model.BookContentEntity
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
        val bookReadingProgress = mSharedPrefs.getInt(Constants.READING_PROGRESS_OF_BOOK_ID + bookId, 0)

        return BookContentEntity(id = bookId, content = bookContent, readingProgress = bookReadingProgress)
    }

    override fun saveReadingProgress(bookId: Int, readingProgress: Int){
        mSharedPrefs.edit().putInt(Constants.READING_PROGRESS_OF_BOOK_ID + bookId, readingProgress).apply()
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