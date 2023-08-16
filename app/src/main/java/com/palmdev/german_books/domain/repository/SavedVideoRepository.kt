package com.palmdev.german_books.domain.repository

import com.palmdev.german_books.domain.model.SavedVideo
import kotlinx.coroutines.flow.Flow

interface SavedVideoRepository {

    suspend fun saveVideo(video: SavedVideo)

    suspend fun getVideoById(videoId: String): SavedVideo?

    suspend fun getAll(): Flow<List<SavedVideo>>

    suspend fun getFavorites(): Flow<List<SavedVideo>>

    suspend fun getLastVideo(): SavedVideo?

    suspend fun deleteAll()

    suspend fun updateVideo(video: SavedVideo)
}