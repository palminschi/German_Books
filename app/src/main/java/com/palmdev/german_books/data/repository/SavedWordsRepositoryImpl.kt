package com.palmdev.german_books.data.repository

import android.util.Log
import com.palmdev.german_books.data.database.SavedWordsDao
import com.palmdev.german_books.data.mapper.SavedWordMapper
import com.palmdev.german_books.domain.model.SavedWord
import com.palmdev.german_books.domain.repository.SavedWordsRepository
import com.palmdev.german_books.utils.CAUGHT_ERROR
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SavedWordsRepositoryImpl @Inject constructor(private val savedWordsDao: SavedWordsDao) :
    SavedWordsRepository {

    override suspend fun getWordsToRepeatCount(currentTime: Long) =
        savedWordsDao.getNeedRepetitionCount(currentTime)

    override suspend fun getWordsCount() = savedWordsDao.getWordsCount()

    override suspend fun saveWord(word: SavedWord) {
        val sameWord = savedWordsDao.getWordByValue(word.value)
        if (sameWord != null) return
        savedWordsDao.saveWord(SavedWordMapper.mapToData(word))
    }

    override suspend fun updateSavedWord(savedWord: SavedWord) {
        savedWordsDao.updateWord(SavedWordMapper.mapToData(savedWord))
    }

    override suspend fun getAllSavedWords(): Flow<List<SavedWord>> {
        return try {
            savedWordsDao.getAllWords().map { entityList ->
                entityList.map { SavedWordMapper.mapToDomain(it) }
            }
        } catch (ex: Exception) {
            ex.message?.let { Log.e(CAUGHT_ERROR, it) }
            emptyFlow()
        }
    }

    override suspend fun getWordsForRepeating(currentTime: Long): Flow<List<SavedWord>> {
        return try {
            savedWordsDao.getWordsForRepetition(currentTime).map { entityList ->
                entityList.map { SavedWordMapper.mapToDomain(it) }
            }
        } catch (ex: Exception) {
            emptyFlow()
        }
    }

    override suspend fun deleteWord(savedWord: SavedWord) {
        savedWordsDao.deleteWord(SavedWordMapper.mapToData(savedWord))
    }

    override suspend fun deleteAllWords() {
        savedWordsDao.deleteAllWords()
    }

    override suspend fun getWordByWordId(wordId: String): SavedWord? {
        val entity = savedWordsDao.getWordByWordId(wordId)
        return if (entity == null) null
        else SavedWordMapper.mapToDomain(entity)
    }
}
