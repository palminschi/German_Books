package com.palmdev.german_books.domain.repository

import com.palmdev.german_books.domain.model.Language
import com.palmdev.german_books.domain.model.Subtitle
import com.palmdev.german_books.domain.model.VideoInfo
import kotlinx.coroutines.flow.Flow

interface VideoRepository {

    suspend fun getAllVideos(): List<VideoInfo>

    suspend fun getSubtitlesById(videoId: String, language: Language): List<Subtitle>

    suspend fun getPopularVideos(): Flow<List<VideoInfo>>

    suspend fun getTopRatedVideos(): Flow<List<VideoInfo>>

    suspend fun getRecommendedVideos(videoId: String?): Flow<List<VideoInfo>>

    suspend fun getVideoById(videoId: String): VideoInfo?
}