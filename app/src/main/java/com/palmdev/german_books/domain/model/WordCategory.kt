package com.palmdev.german_books.domain.model

data class WordCategory(
    val id: Int,
    val categoryId: Int,
    val categoryName: String,
    val categoryIconUrl: String,
    val progress: Int,
    val wordsId: List<String>
): java.io.Serializable
