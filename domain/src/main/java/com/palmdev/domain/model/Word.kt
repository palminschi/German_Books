package com.palmdev.domain.model

data class Word(
    val id: Long = 0,
    val word: String,
    val translation: String,
    val group: Int = 0
)
