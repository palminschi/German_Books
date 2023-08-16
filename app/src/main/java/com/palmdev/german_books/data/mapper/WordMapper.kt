package com.palmdev.german_books.data.mapper

import com.palmdev.german_books.data.model.WordDto
import com.palmdev.german_books.domain.model.Word

object WordMapper {

    fun toDomain(wordDto: WordDto, translatedWordDto: WordDto, englishWordDto: WordDto): Word {
        return Word(
            wordId = englishWordDto.wordId,
            value = wordDto.value,
            translation = translatedWordDto.value,
            transcription = wordDto.transcription,
            imageUrl = englishWordDto.imageUrl,
            example = wordDto.example,
            type = wordDto.type?.let { name -> Word.WordType.values().first { it.string == name } },
            conjugation = wordDto.conjugation
        )
    }
}