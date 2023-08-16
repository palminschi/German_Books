package com.palmdev.german_books.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.palmdev.german_books.data.converter.SubtitleDtoConverter
import com.palmdev.german_books.data.model.SavedVideoEntity

@Database(entities = [SavedVideoEntity::class], version = 1, exportSchema = false)
@TypeConverters(SubtitleDtoConverter::class)
abstract class SavedVideoDatabase : RoomDatabase() {

    abstract fun getDao(): SavedVideoDao

}