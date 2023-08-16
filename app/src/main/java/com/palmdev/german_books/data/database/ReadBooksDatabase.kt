package com.palmdev.german_books.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.palmdev.german_books.domain.model.ReadBook

@Database(entities = [ReadBook::class], version = 1, exportSchema = false)
abstract class ReadBooksDatabase : RoomDatabase() {
    abstract fun getDao(): ReadBooksDao
}