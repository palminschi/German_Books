package com.palmdev.german_books.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "words_table")
data class WordDto(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "word_id")
    val wordId: String,
    val value: String,
    val language: String,
    val transcription: String? = null,
    val example: String? = null,
    @ColumnInfo(name = "image_url")
    val imageUrl: String? = null,
    val type: String? = null,
    val conjugation: String? = null
)
