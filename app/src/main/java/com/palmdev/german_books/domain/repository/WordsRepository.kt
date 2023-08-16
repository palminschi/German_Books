package com.palmdev.german_books.domain.repository

import com.palmdev.german_books.domain.model.Word
import kotlinx.coroutines.flow.Flow

interface WordsRepository {

    suspend fun getWordById(wordId: String): Word?

    suspend fun getWordsByIdList(list: List<String>): Flow<List<Word>>

}