package com.palmdev.german_books.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books_table")
data class Book(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val author: String,
    val level: String,
    @ColumnInfo(name = "category_id")
    val categoryId: Int,
    @ColumnInfo(name = "is_premium")
    val isPremium: Boolean,
    @ColumnInfo(name = "image_path")
    val imagePath: String,
    @ColumnInfo(name = "content_path")
    val contentPath: String
)
