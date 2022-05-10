package com.palmdev.domain.usecase.words

import com.palmdev.domain.model.Word
import com.palmdev.domain.repository.WordsRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.runBlocking

class AddWordUseCase(private val wordsRepository: WordsRepository) {

    suspend fun invoke(
        word: String,
        translation: String,
        lastWord: Word?
    ) {
        var group = 0
        // Every 10th word - group++
        if (lastWord != null) {
            group = lastWord.group
            val lastId = lastWord.id.toInt()
            if (lastId % 10 == 0) {
                group = lastWord.group + 1
            }
        }

        wordsRepository.addWord(
            Word(
                word = word,
                translation = translation,
                group = group
            )
        )
    }
}