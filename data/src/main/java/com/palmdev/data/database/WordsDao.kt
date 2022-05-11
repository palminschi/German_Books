package com.palmdev.data.database

import androidx.room.*
import com.palmdev.data.database.model.WordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WordsDao {

    @Transaction
    @Query("SELECT * FROM words_table")
    fun getAllWords(): Flow<List<WordEntity>?>

    @Query("SELECT * FROM words_table WHERE `group` = :group")
    fun getWordsByGroup(group: Int): Flow<List<WordEntity>?>

    @Query("SELECT * FROM words_table WHERE id = (SELECT MAX(id) FROM words_table)")
    suspend fun getLastWord(): WordEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWord(word: WordEntity)

    @Query("DELETE FROM words_table WHERE `group` = :group")
    suspend fun deleteByGroup(group: Int)
}