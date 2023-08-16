package com.palmdev.german_books.domain.usecases.video

import com.palmdev.german_books.domain.model.SavedVideo
import com.palmdev.german_books.domain.repository.SavedVideoRepository
import javax.inject.Inject

class UpdateVideoIsFavoriteUseCase @Inject constructor(private val savedVideoRepository: SavedVideoRepository) {

    suspend operator fun invoke(video: SavedVideo) {
        savedVideoRepository.updateVideo(video)
    }

}