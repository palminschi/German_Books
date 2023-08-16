package com.palmdev.german_books.domain.model

import java.io.Serializable

data class Word(
    val id: Int? = null,
    val wordId: String,
    val value: String,
    val translation: String,
    val transcription: String? = null,
    val imageUrl: String? = null,
    val example: String? = null,
    val type: WordType? = null,
    val conjugation: String? = null
) : Serializable {

    enum class WordType(val string: String) {
        VERB("verb"),
        NOUN("noun"),
        ADJECTIVE("adjective"),
        ADVERB("adverb"),
        NOT_SET("not_set")
    }

    fun toSavedWord(nextRepetitionTime: Long, repetitionCounter: Int = 1): SavedWord {
        return SavedWord(
            value = this.value,
            wordId = this.wordId,
            translation = this.translation,
            transcription = this.transcription,
            imageUrl = this.imageUrl,
            example = this.example,
            type = this.type,
            conjugation = this.conjugation,
            repetitionCounter = repetitionCounter,
            nextRepetitionTime = nextRepetitionTime
        )
    }
}
