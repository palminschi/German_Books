package com.palmdev.german_books.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "words_categories_table")
data class WordCategoryDto(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val category_id: Int,
    val icon_url: String,
    val progress: Int,
    @ColumnInfo(name = "words_id")
    val wordsId: String
)
