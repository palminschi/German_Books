package com.palmdev.domain.repository

import com.palmdev.domain.model.Word
import kotlinx.coroutines.flow.Flow

interface WordsRepository {

    suspend fun getAllWords(): Flow<List<Word>>

    suspend fun getLastWord(): Word?

    suspend fun addWord(word: Word)

    suspend fun getWordsByGroup(groupId: Int): Flow<List<Word>>

    suspend fun deleteByGroup(groupId: Int)
}