package com.palmdev.german_books.data.repository

import android.util.Log
import com.palmdev.german_books.data.database.SavedVideoDao
import com.palmdev.german_books.domain.model.SavedVideo
import com.palmdev.german_books.domain.repository.SavedVideoRepository
import com.palmdev.german_books.utils.CAUGHT_ERROR
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SavedVideoRepositoryImpl @Inject constructor(private val savedVideoDao: SavedVideoDao) :
    SavedVideoRepository {

    override suspend fun saveVideo(video: SavedVideo) {
        val existingVideo = savedVideoDao.getVideoById(videoId = video.videoId)

        savedVideoDao.insertOrReplace(video.toData())

        existingVideo?.let { savedVideoDao.deleteVideo(it) }
    }

    override suspend fun getVideoById(videoId: String): SavedVideo? {
        return try {
            savedVideoDao.getVideoById(videoId)?.toDomain()
        } catch (e: Exception) {
            e.message?.let { Log.e(CAUGHT_ERROR, it) }
            null
        }
    }

    override suspend fun getAll(): Flow<List<SavedVideo>> {
        return try {
            savedVideoDao.getAll().map { list ->
                list.map { it.toDomain() }
            }
        } catch (e: Exception) {
            e.message?.let { Log.e(CAUGHT_ERROR, it) }
            emptyFlow()
        }
    }

    override suspend fun getFavorites(): Flow<List<SavedVideo>> {
        return try {
            savedVideoDao.getFavoriteVideos().map { list ->
                list.map { it.toDomain() }
            }
        } catch (e: Exception) {
            e.message?.let { Log.e(CAUGHT_ERROR, it) }
            emptyFlow()
        }
    }

    override suspend fun getLastVideo(): SavedVideo? {
        return try {
            savedVideoDao.getLastVideo()?.toDomain()
        } catch (e: Exception) {
            e.message?.let { Log.e(CAUGHT_ERROR, it) }
            null
        }
    }

    override suspend fun deleteAll() {
        savedVideoDao.deleteAll()
    }

    override suspend fun updateVideo(video: SavedVideo) {
        savedVideoDao.updateVideo(video = video.toData())
    }

}