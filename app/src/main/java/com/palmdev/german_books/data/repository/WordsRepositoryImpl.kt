package com.palmdev.german_books.data.repository

import android.util.Log
import com.palmdev.german_books.data.database.WordsDao
import com.palmdev.german_books.data.mapper.WordMapper
import com.palmdev.german_books.domain.model.Word
import com.palmdev.german_books.domain.repository.UserRepository
import com.palmdev.german_books.domain.repository.WordsRepository
import com.palmdev.german_books.utils.CAUGHT_ERROR
import com.palmdev.german_books.utils.Languages
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class WordsRepositoryImpl @Inject constructor(
    private val wordsDao: WordsDao,
    private val userRepository: UserRepository
) : WordsRepository {

    override suspend fun getWordById(wordId: String): Word? {
        return try {
            val word = wordsDao.getWordByIdAndLang(wordId, Languages.learningLanguage.code)
            val translatedWord =
                wordsDao.getWordByIdAndLang(wordId, userRepository.userLanguage.code)
            val englishWord = wordsDao.getWordByIdAndLang(wordId, Languages.EN.code)

            if (word == null || translatedWord == null || englishWord == null) null
            else {
                WordMapper.toDomain(
                    wordDto = word,
                    translatedWordDto = translatedWord,
                    englishWordDto = englishWord
                )
            }
        } catch (e: Exception) {
            e.message?.let { Log.e(CAUGHT_ERROR, it) }
            null
        }
    }

    override suspend fun getWordsByIdList(list: List<String>): Flow<List<Word>> {
        val words = ArrayList<Word>()
        list.forEach { id ->
            getWordById(id)?.let { word -> words.add(word) }
        }
        return flowOf(words)
    }

}