package com.palmdev.german_books.data.database

import androidx.room.*
import com.palmdev.german_books.data.model.SavedVideoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedVideoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrReplace(savedVideoEntity: SavedVideoEntity)

    @Transaction
    @Query("SELECT * FROM saved_video_table WHERE video_id = :videoId")
    fun getVideoById(videoId: String): SavedVideoEntity?

    @Transaction
    @Query("SELECT * FROM saved_video_table")
    fun getAll(): Flow<List<SavedVideoEntity>>

    @Transaction
    @Query("SELECT * FROM saved_video_table WHERE is_favorite = 1")
    fun getFavoriteVideos(): Flow<List<SavedVideoEntity>>

    @Query("SELECT * FROM saved_video_table ORDER BY id DESC LIMIT 1")
    fun getLastVideo(): SavedVideoEntity?

    @Delete
    fun deleteVideo(video: SavedVideoEntity)

    @Query("DELETE FROM saved_video_table")
    fun deleteAll()

    @Update
    fun updateVideo(video: SavedVideoEntity)
}