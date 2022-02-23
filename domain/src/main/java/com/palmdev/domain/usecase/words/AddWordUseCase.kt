package com.palmdev.domain.usecase.words

import com.palmdev.domain.model.Word
import com.palmdev.domain.repository.WordsRepository

class AddWordUseCase(private val wordsRepository: WordsRepository) {

    suspend fun invoke(
        word: String,
        translation: String,
        sentence: String? = null
    ){
        var group = 0
        val lastWord = wordsRepository.getLastWord()

        if (lastWord != null) {
            group = lastWord.group

            // Every 10th word - group++
            val lastId = lastWord.id.toInt()
            if (lastId % 10 == 0) {
                group = lastWord.group + 1
            }
        }
        wordsRepository.addWord(
            Word(
                word = word,
                translation = translation,
                sentence = sentence,
                group = group
            )
        )
    }
}