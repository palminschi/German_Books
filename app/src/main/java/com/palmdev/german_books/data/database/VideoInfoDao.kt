package com.palmdev.german_books.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.palmdev.german_books.data.model.VideoInfoDto
import kotlinx.coroutines.flow.Flow

@Dao
interface VideoInfoDao {

    @Transaction
    @Query("SELECT * FROM video_table WHERE video_id = :videoId")
    fun getVideoById(videoId: String): VideoInfoDto?

    @Transaction
    @Query("SELECT * FROM video_table")
    fun getAll(): List<VideoInfoDto>

    @Transaction
    @Query("SELECT * FROM video_table ORDER BY video_id LIMIT :limit OFFSET :offset")
    fun getVideosByPage(limit: Int, offset: Int): Flow<List<VideoInfoDto>>

    @Transaction
    @Query("SELECT COUNT(*) FROM video_table")
    fun getCount(): Int

    @Transaction
    @Query("SELECT * FROM video_table ORDER BY RANDOM() LIMIT :limit")
    fun getRandomVideos(limit: Int): Flow<List<VideoInfoDto>>

    @Transaction
    @Query("SELECT * FROM video_table WHERE category_id = :category_id")
    fun getVideoByCategory(category_id: Int): Flow<List<VideoInfoDto>>
}