package com.palmdev.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.palmdev.data.database.model.WordEntity

@Database(entities = [WordEntity::class], version = 1, exportSchema = false)
abstract class WordsDatabase : RoomDatabase() {

    abstract fun wordsDao(): WordsDao

}