package com.palmdev.german_books.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.palmdev.german_books.data.model.SavedWordEntity

@Database(entities = [SavedWordEntity::class], version = 2, exportSchema = false)
abstract class SavedWordsDatabase : RoomDatabase() {

    abstract fun getSavedWordsDao(): SavedWordsDao

}