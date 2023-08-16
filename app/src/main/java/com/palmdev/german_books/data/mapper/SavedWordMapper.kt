package com.palmdev.german_books.data.mapper

import com.palmdev.german_books.data.model.SavedWordEntity
import com.palmdev.german_books.domain.model.SavedWord
import com.palmdev.german_books.domain.model.Word

object SavedWordMapper {
    fun mapToDomain(entity: SavedWordEntity): SavedWord {
        return SavedWord(
            id = entity.id,
            value = entity.value,
            translation = entity.translation,
            transcription = entity.transcription,
            imageUrl = entity.imageUrl,
            example = entity.example,
            conjugation = entity.conjugation,
            repetitionCounter = entity.repetitionCounter,
            nextRepetitionTime = entity.nextRepetitionTime,
            type = entity.type?.let { name -> Word.WordType.values().first { name == it.string } },
            wordId = entity.wordId
        )
    }

    fun mapToData(savedWord: SavedWord): SavedWordEntity {
        return SavedWordEntity(
            id = savedWord.id,
            value = savedWord.value,
            translation = savedWord.translation,
            transcription = savedWord.transcription,
            imageUrl = savedWord.imageUrl,
            example = savedWord.example,
            conjugation = savedWord.conjugation,
            repetitionCounter = savedWord.repetitionCounter,
            nextRepetitionTime = savedWord.nextRepetitionTime,
            type = savedWord.type?.string,
            wordId = savedWord.wordId
        )
    }
}
