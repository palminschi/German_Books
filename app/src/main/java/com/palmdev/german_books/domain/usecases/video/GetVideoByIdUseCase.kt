package com.palmdev.german_books.domain.usecases.video

import android.util.Log
import com.palmdev.german_books.domain.model.SavedVideo
import com.palmdev.german_books.domain.repository.SavedVideoRepository
import com.palmdev.german_books.domain.repository.UserRepository
import com.palmdev.german_books.domain.repository.VideoRepository
import com.palmdev.german_books.utils.CAUGHT_ERROR
import com.palmdev.german_books.utils.Languages
import javax.inject.Inject

class GetVideoByIdUseCase @Inject constructor(
    private val savedVideoRepository: SavedVideoRepository,
    private val videoRepository: VideoRepository,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(videoId: String): SavedVideo? {

        val savedVideo = savedVideoRepository.getVideoById(videoId)
        savedVideo?.let { return it }

        val videoInfo = videoRepository.getVideoById(videoId)
        val originalSubtitles = videoRepository.getSubtitlesById(videoId, Languages.learningLanguage)
        val translatedSubtitles =
            videoRepository.getSubtitlesById(videoId, userRepository.userLanguage)

        if (videoInfo != null) {
            val video = SavedVideo(
                videoId = videoInfo.videoId,
                category = videoInfo.category,
                title = videoInfo.title,
                imageUrl = videoInfo.imageUrl,
                duration = videoInfo.duration,
                viewsCount = videoInfo.viewsCount,
                likesCount = videoInfo.likesCount,
                originalSubtitles = originalSubtitles,
                translatedSubtitles = translatedSubtitles
            )
            savedVideoRepository.saveVideo(video)
            return video
        } else {
            Log.e(CAUGHT_ERROR, "Failed to get Video or Subtitles")
            return null
        }
    }
}