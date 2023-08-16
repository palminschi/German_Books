package com.palmdev.german_books.domain.repository

import com.palmdev.german_books.domain.model.SavedWord
import kotlinx.coroutines.flow.Flow

interface SavedWordsRepository {

    suspend fun getWordsCount(): Int

    suspend fun getWordsToRepeatCount(currentTime: Long): Int

    suspend fun saveWord(word: SavedWord)

    suspend fun updateSavedWord(savedWord: SavedWord)

    suspend fun getAllSavedWords(): Flow<List<SavedWord>>

    suspend fun getWordsForRepeating(currentTime: Long): Flow<List<SavedWord>>

    suspend fun deleteWord(savedWord: SavedWord)

    suspend fun deleteAllWords()

    suspend fun getWordByWordId(wordId: String): SavedWord?
}