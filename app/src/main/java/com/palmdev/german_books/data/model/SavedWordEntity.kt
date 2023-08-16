package com.palmdev.german_books.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_words_table")
data class SavedWordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    @ColumnInfo(name = "word_id")
    val wordId: String,
    val value: String,
    val translation: String,
    val transcription: String? = null,
    @ColumnInfo(name = "image_url")
    val imageUrl: String? = null,
    val example: String? = null,
    val type: String? = null,
    val conjugation: String? = null,
    @ColumnInfo(name = "repetition_counter")
    val repetitionCounter: Int = 1,
    @ColumnInfo(name = "next_repetition_time")
    val nextRepetitionTime: Long
)
