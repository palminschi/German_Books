package com.palmdev.german_books.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.palmdev.german_books.data.converter.SubtitleDtoConverter
import com.palmdev.german_books.domain.model.SavedVideo
import com.palmdev.german_books.domain.model.VideoCategory

@Entity(tableName = "saved_video_table")
data class SavedVideoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    @ColumnInfo(name = "video_id")
    val videoId: String,
    val category_id: Int,
    val title: String,
    @ColumnInfo(name = "image_url")
    val imageUrl: String,
    val duration: Long,
    @ColumnInfo(name = "views_count")
    val viewsCount: Int,
    @ColumnInfo(name = "likes_count")
    val likesCount: Int,
    @ColumnInfo(name = "seconds_watched")
    val secondsWatched: Double,
    @ColumnInfo(name = "original_subtitles")
    @TypeConverters(SubtitleDtoConverter::class)
    val originalSubtitles: List<SubtitleDto>,
    @ColumnInfo(name = "translated_subtitles")
    @TypeConverters(SubtitleDtoConverter::class)
    val translatedSubtitles: List<SubtitleDto>,
    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean = false
) {

    fun toDomain(): SavedVideo {
        return SavedVideo(
            id = this.id,
            videoId = this.videoId,
            category = VideoCategory.values().first { it.id == this.category_id },
            title = this.title,
            imageUrl = this.imageUrl,
            duration = this.duration,
            viewsCount = this.viewsCount,
            likesCount = this.likesCount,
            secondsWatched = this.secondsWatched,
            originalSubtitles = this.originalSubtitles.map { it.toDomain() },
            translatedSubtitles = this.translatedSubtitles.map { it.toDomain() },
            isFavorite = this.isFavorite
        )
    }

}
