package com.palmdev.german_books.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.palmdev.german_books.data.model.WordDto

@Database(entities = [WordDto::class], version = 1, exportSchema = false)
abstract class WordsDatabase : RoomDatabase() {
    abstract fun getDao(): WordsDao
}