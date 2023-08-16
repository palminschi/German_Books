package com.palmdev.german_books.domain.repository

import com.palmdev.german_books.domain.model.WordCategory

interface WordsCategoriesRepository {

    suspend fun getAllCategories(): List<WordCategory>

    suspend fun updateCategory(wordCategory: WordCategory)

}