package com.palmdev.domain.usecase.words

import com.palmdev.domain.model.Word
import com.palmdev.domain.repository.WordsRepository
import kotlinx.coroutines.flow.Flow

class GetWordsByGroupUseCase(private val wordsRepository: WordsRepository) {

    suspend fun invoke(groupId: Int): Flow<List<Word>> {
        return wordsRepository.getWordsByGroup(groupId)
    }
}