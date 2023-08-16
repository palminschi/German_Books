package com.palmdev.german_books.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.palmdev.german_books.domain.model.ReadBook

@Dao
interface ReadBooksDao {

    @Transaction
    @Query("SELECT * FROM read_books_table")
    fun getAll(): List<ReadBook>

    @Query("SELECT * FROM read_books_table WHERE id = :id")
    fun getBookById(id: Int): ReadBook?

    @Transaction
    @Query("SELECT * FROM read_books_table WHERE is_favorite") // = 1
    fun getFavoriteBooks(): List<ReadBook>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(readBook: ReadBook)
}