package com.palmdev.domain.usecase.words

import com.palmdev.domain.repository.WordsRepository

class DeleteByGroupUseCase(private val wordsRepository: WordsRepository) {

    suspend fun execute(groupId: Int){
        wordsRepository.deleteByGroup(groupId = groupId)
    }

}