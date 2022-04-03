package com.palmdev.data.repository

import com.palmdev.data.database.WordsDao
import com.palmdev.data.database.model.WordEntity
import com.palmdev.domain.model.Word
import com.palmdev.domain.repository.WordsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map

class WordsRepositoryImpl(private val wordsDao: WordsDao) : WordsRepository {

    override suspend fun getAllWords(): Flow<List<Word>> {
        return mapListOfWordsToDomain(
            wordsDao.getAllWords()
        )
    }

    override suspend fun getWordsByGroup(groupId: Int): Flow<List<Word>> {
        return mapListOfWordsToDomain(
            wordsDao.getWordsByGroup(group = groupId)
        )
    }

    override suspend fun getLastWord(): Word? {
        return wordsDao.getLastWord()?.let {
            mapWordToDomain(
                it
            )
        }
    }

    override suspend fun addWord(word: Word) {
        wordsDao.addWord(
            word = mapWordToData(word)
        )
    }




    // Mappers
    private fun mapListOfWordsToDomain(dataWords: Flow<List<WordEntity>?>): Flow<List<Word>> {
        val domainWords: Flow<List<Word>> = dataWords.map { list ->
            list?.map {
                mapWordToDomain(it)
            } ?: emptyList()
        }
        return domainWords
    }

    private fun mapWordToDomain(wordEntity: WordEntity): Word{
        return Word(
            id = wordEntity.id,
            word = wordEntity.word,
            translation = wordEntity.translation,
            group = wordEntity.group
        )
    }

    private fun mapWordToData(word: Word): WordEntity {
        return WordEntity(
            id = word.id,
            word = word.word,
            translation = word.translation,
            group = word.group
        )
    }
}