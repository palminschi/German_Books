package com.palmdev.german_books.domain.model

data class VideoInfo(
    val id: Int? = null,
    val videoId: String,
    val category: VideoCategory,
    val title: String,
    val imageUrl: String,
    val duration: Long,
    val viewsCount: Int,
    val likesCount: Int,
): java.io.Serializable
