package com.palmdev.german_books.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.palmdev.german_books.domain.model.Book

@Dao
interface BooksDao {

    @Transaction
    @Query("SELECT * FROM books_table")
    fun getAllBooks(): List<Book>

    @Transaction
    @Query("SELECT * FROM books_table WHERE level = :level")
    fun getBooksByLevel(level: String): List<Book>

    @Query("SELECT * FROM books_table WHERE id = :id")
    fun getBookById(id: Int): Book?
}