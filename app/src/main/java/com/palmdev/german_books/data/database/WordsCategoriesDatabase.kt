package com.palmdev.german_books.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.palmdev.german_books.data.model.WordCategoryDto

@Database(entities = [WordCategoryDto::class], version = 1, exportSchema = false)
abstract class WordsCategoriesDatabase : RoomDatabase() {
    abstract fun getDao(): WordsCategoriesDao
}