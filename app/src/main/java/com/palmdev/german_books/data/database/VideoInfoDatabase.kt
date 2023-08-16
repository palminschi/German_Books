package com.palmdev.german_books.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.palmdev.german_books.data.model.VideoInfoDto

@Database(entities = [VideoInfoDto::class], version = 3, exportSchema = false)
abstract class VideoInfoDatabase : RoomDatabase() {
    abstract fun getDao(): VideoInfoDao
}