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

            val totalWords = list.size
            var totalGroups = 0

            var numberOfWordsFirstGroup = 0
            list.map {
                if (it.group == list.first().group) numberOfWordsFirstGroup++
            }
            array.add(
                GroupOfWords(
                    groupId = list.first().group,
                    numberOfWords = numberOfWordsFirstGroup
                )
            )

            for (i in list.indices) {
                val currentWord = list[i]
                val previousWord = if (i == 0) list[0] else list[i - 1]
                if (currentWord.group > previousWord.group) {
                    totalGroups++

                    var numberOfWordsInGroup = 0
                    list.map {
                        if (it.group == currentWord.group) numberOfWordsInGroup++
                    }
                    array.add(
                        GroupOfWords(
                            groupId = currentWord.group,
                            numberOfWords = numberOfWordsInGroup
                        )
                    )
                }
            }


        }

        return array
    }
}