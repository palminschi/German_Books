package com.palmdev.german_books.data.repository

import android.util.Log
import com.palmdev.german_books.data.database.VideoInfoDao
import com.palmdev.german_books.data.network.SubtitlesFirebaseStorage
import com.palmdev.german_books.domain.model.Language
import com.palmdev.german_books.domain.model.Subtitle
import com.palmdev.german_books.domain.model.VideoCategory
import com.palmdev.german_books.domain.model.VideoInfo
import com.palmdev.german_books.domain.repository.VideoRepository
import com.palmdev.german_books.utils.CAUGHT_ERROR
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class VideoRepositoryImpl @Inject constructor(
    private val subtitlesFirebaseStorage: SubtitlesFirebaseStorage,
    private val videoInfoDao: VideoInfoDao
) : VideoRepository {

    override suspend fun getAllVideos(): List<VideoInfo> {
        return try {
            videoInfoDao.getAll().map {
                it.toDomain()
            }
        } catch (e: Exception) {
            e.message?.let { Log.e(CAUGHT_ERROR, it) }
            emptyList()
        }
    }

    override suspend fun getSubtitlesById(
        videoId: String,
        language: Language
    ): List<Subtitle> {
        return subtitlesFirebaseStorage.getSubtitles(videoId, language.code).map { it.toDomain() }
    }

    override suspend fun getPopularVideos(): Flow<List<VideoInfo>> {
        return videoInfoDao.getVideoByCategory(VideoCategory.POPULAR.id).map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun getTopRatedVideos(): Flow<List<VideoInfo>> {
        return videoInfoDao.getVideoByCategory(VideoCategory.TOP_RATED.id).map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun getRecommendedVideos(videoId: String?): Flow<List<VideoInfo>> {
        return videoInfoDao.getRandomVideos(25).map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun getVideoById(videoId: String): VideoInfo? {
        return videoInfoDao.getVideoById(videoId)?.toDomain()
    }
}