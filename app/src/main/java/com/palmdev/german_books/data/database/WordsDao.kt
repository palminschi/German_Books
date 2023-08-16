package com.palmdev.german_books.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.palmdev.german_books.data.model.WordDto

@Dao
interface WordsDao {

    @Transaction
    @Query("SELECT * FROM words_table WHERE word_id = :wordId AND language = :languageCode")
    fun getWordByIdAndLang(wordId: String, languageCode: String): WordDto?

}