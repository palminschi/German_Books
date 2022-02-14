package com.palmdev.data.storage.model

data class BookContentEntity(
    val id: Int,
    val content: String,
    val readingProgress: Int = 0
)
