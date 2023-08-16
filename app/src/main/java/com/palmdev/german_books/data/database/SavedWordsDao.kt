package com.palmdev.german_books.data.database

import androidx.room.*
import com.palmdev.german_books.data.model.SavedWordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedWordsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveWord(savedWordEntity: SavedWordEntity)

    @Transaction
    @Query("SELECT * FROM saved_words_table WHERE value = :value")
    fun getWordByValue(value: String): SavedWordEntity?

    @Transaction
    @Query("SELECT * FROM saved_words_table")
    fun getAllWords(): Flow<List<SavedWordEntity>>

    @Transaction
    @Query("SELECT * FROM saved_words_table WHERE next_repetition_time <= :currentTime")
    fun getWordsForRepetition(currentTime: Long): Flow<List<SavedWordEntity>>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateWord(word: SavedWordEntity)

    @Delete
    fun deleteWord(word: SavedWordEntity)

    @Query("DELETE FROM saved_words_table")
    fun deleteAllWords()

    @Transaction
    @Query("SELECT COUNT(id) FROM saved_words_table")
    fun getWordsCount(): Int

    @Transaction
    @Query("SELECT COUNT(*) FROM saved_words_table WHERE next_repetition_time <= :currentTime")
    fun getNeedRepetitionCount(currentTime: Long): Int

    @Transaction
    @Query("SELECT * FROM saved_words_table WHERE word_id = :wordId")
    fun getWordByWordId(wordId: String): SavedWordEntity?
}