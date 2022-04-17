package com.palmdev.domain.model

data class Book(
    val id: Int,
    val title: String,
    val author: String,
    val difficulty: String,
    val encodedImage: String,
    val favorite: Boolean = false,
    val isPremium: Boolean
)
