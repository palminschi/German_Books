package com.palmdev.domain.model

data class BookContent(
    val id: Int,
    val content: String,
    val readingProgress: Int = 0
)
