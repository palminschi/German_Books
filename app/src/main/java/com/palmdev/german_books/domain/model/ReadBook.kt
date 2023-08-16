package com.palmdev.german_books.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "read_books_table")
data class ReadBook(
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
    val contentPath: String,
    @ColumnInfo(name = "current_page")
    val currentPage: Int,
    @ColumnInfo(name = "total_pages")
    val totalPages: Int,
    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean
)
