package com.palmdev.german_books.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.palmdev.german_books.domain.model.VideoCategory
import com.palmdev.german_books.domain.model.VideoInfo
import org.threeten.bp.Duration

@Entity(tableName = "video_table")
data class VideoInfoDto(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val video_id: String,
    val category_id: Int,
    val title: String,
    val image_url: String,
    val duration: String, // Format: PT15M3S
    val views_count: Int,
    val likes_count: Int,
) {

    fun toDomain(): VideoInfo {
        val convertedDuration = Duration.parse(duration).toMillis()
        return VideoInfo(
            id = id,
            videoId = video_id,
            category = VideoCategory.values().first { it.id == category_id },
            title = title,
            imageUrl = image_url,
            duration = convertedDuration,
            viewsCount = views_count,
            likesCount = likes_count
        )
    }

}