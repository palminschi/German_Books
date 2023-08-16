package com.palmdev.german_books.data.model

import com.palmdev.german_books.domain.model.Subtitle

data class SubtitleDto(
    val number: Int,
    val startTime: Double,
    val endTime: Double,
    val text: String
) {
    fun toDomain(): Subtitle {
        return Subtitle(
            number, startTime, endTime, text
        )
    }
}
