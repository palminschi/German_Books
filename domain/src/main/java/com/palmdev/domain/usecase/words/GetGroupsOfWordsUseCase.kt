package com.palmdev.domain.usecase.words

import com.palmdev.domain.model.GroupOfWords
import com.palmdev.domain.model.Word
import com.palmdev.domain.repository.WordsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetGroupsOfWordsUseCase(private val wordsRepository: WordsRepository) {

    suspend fun invoke(): Flow<List<GroupOfWords>> {

        val listOfGroups: Flow<List<GroupOfWords>> = wordsRepository.getAllWords().map { list ->
            divideToGroups(list)
        }

        return listOfGroups
    }

    // Divide list of all words into groups of 10 words
    private fun divideToGroups(list: List<Word>): List<GroupOfWords> {
        val array = ArrayList<GroupOfWords>()

        if (list.isNotEmpty()) {
            val totalWords = list.last().id.toInt()
            val totalGroups = list.last().group

            for (i in 0..totalGroups) {
                val numberOfWords = if (i == totalGroups) {
                    totalWords - (totalGroups * 10)
                } else 10

                array.add(
                    GroupOfWords(
                        groupId = i,
                        numberOfWords = numberOfWords
                    )
                )
            }
        }

        return array
    }
}