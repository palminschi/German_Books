package com.palmdev.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.palmdev.data.util.Constants

@Entity(tableName = Constants.DATABASE_TABLE)
data class WordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val word: String,
    val translation: String,
    val sentence: String? = null,
    val group: Int = 0
)
