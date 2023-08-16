package com.palmdev.german_books.domain.model

import com.palmdev.german_books.data.model.SubtitleDto

data class Subtitle(
    val number: Int,
    val startTime: Double,
    val endTime: Double,
    val text: String
): java.io.Serializable {

    fun toData(): SubtitleDto {
        return SubtitleDto(number, startTime, endTime, text)
    }

}
