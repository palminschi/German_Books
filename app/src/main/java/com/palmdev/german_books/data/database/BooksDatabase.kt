package com.palmdev.german_books.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.palmdev.german_books.domain.model.Book

@Database(entities = [Book::class], version = 1, exportSchema = false)
abstract class BooksDatabase : RoomDatabase() {
    abstract fun getBooksDao() : BooksDao
}