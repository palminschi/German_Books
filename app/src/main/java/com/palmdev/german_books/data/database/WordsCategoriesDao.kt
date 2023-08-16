package com.palmdev.german_books.data.database

import androidx.room.*
import com.palmdev.german_books.data.model.WordCategoryDto

@Dao
interface WordsCategoriesDao {

    @Transaction
    @Query("SELECT * FROM words_categories_table")
    suspend fun getAll(): List<WordCategoryDto>


    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateCategory(category: WordCategoryDto)
}