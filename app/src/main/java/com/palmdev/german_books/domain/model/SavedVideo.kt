package com.palmdev.german_books.domain.model

import com.palmdev.german_books.data.model.SavedVideoEntity

data class SavedVideo(
    val id: Int? = null,
    val videoId: String,
    val category: VideoCategory,
    val title: String,
    val imageUrl: String,
    val duration: Long,
    val viewsCount: Int,
    val likesCount: Int,
    val secondsWatched: Double = 0.0,
    val originalSubtitles: List<Subtitle>,
    val translatedSubtitles: List<Subtitle>,
    val isFavorite: Boolean = false
) : java.io.Serializable {

    fun toData(): SavedVideoEntity {
        return SavedVideoEntity(
            videoId = this.videoId,
            category_id = this.category.id,
            title = this.title,
            imageUrl = this.imageUrl,
            duration = this.duration,
            viewsCount = this.viewsCount,
            likesCount = this.likesCount,
            secondsWatched = this.secondsWatched,
            originalSubtitles = this.originalSubtitles.map { it.toData() },
            translatedSubtitles = this.translatedSubtitles.map { it.toData() },
            isFavorite = this.isFavorite
        )
    }
}