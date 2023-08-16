package com.palmdev.german_books.domain.model

data class SavedWord(
    val id: Int? = null,
    val wordId: String,
    val value: String,
    val translation: String,
    val transcription: String? = null,
    val imageUrl: String? = null,
    val example: String? = null,
    val type: Word.WordType? = null,
    val conjugation: String? = null,
    val repetitionCounter: Int = 1,
    val nextRepetitionTime: Long
) {
    enum class Difficulty {
        DEFAULT, EASY, MEDIUM, HARD
    }
}


