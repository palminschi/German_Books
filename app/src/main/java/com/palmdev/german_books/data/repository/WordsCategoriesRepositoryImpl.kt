package com.palmdev.german_books.data.repository

import android.content.Context
import com.palmdev.german_books.data.database.WordsCategoriesDao
import com.palmdev.german_books.data.mapper.WordCategoryMapper
import com.palmdev.german_books.domain.model.WordCategory
import com.palmdev.german_books.domain.repository.WordsCategoriesRepository
import javax.inject.Inject

class WordsCategoriesRepositoryImpl @Inject constructor(
    private val wordsCategoriesDao: WordsCategoriesDao,
    private val context: Context
) : WordsCategoriesRepository {

    private val mapper = WordCategoryMapper

    override suspend fun getAllCategories(): List<WordCategory> {
        return wordsCategoriesDao.getAll().map {
            mapper.toDomain(it, context)
        }
    }

    override suspend fun updateCategory(wordCategory: WordCategory) {
        wordsCategoriesDao.updateCategory(mapper.toData(wordCategory))
    }

}