package com.palmdev.german_books.domain.usecases.words

import com.palmdev.german_books.domain.model.SavedWord
import com.palmdev.german_books.domain.repository.SavedWordsRepository
import java.util.Calendar
import javax.inject.Inject
import kotlin.math.pow

class UpdateSavedWordTimeUseCase @Inject constructor(private val savedWordsRepository: SavedWordsRepository) {

    suspend operator fun invoke(
        savedWord: SavedWord,
        difficulty: SavedWord.Difficulty = SavedWord.Difficulty.DEFAULT
    ) {
        var repetitionCounter = savedWord.repetitionCounter
        val currentTime = Calendar.getInstance().timeInMillis
        val nextTime: Long
        val updatedSavedWord = when (difficulty) {
            SavedWord.Difficulty.DEFAULT -> {
                nextTime = currentTime + (60 * 60 * 1000) // + 60 min
                wordSetNewRepetition(
                    savedWord,
                    repetitionCounter = 1,
                    nextRepetitionTime = nextTime
                )
            }
            SavedWord.Difficulty.HARD -> {
                nextTime = currentTime + (1 * 60 * 1000) // + 1 min
                wordSetNewRepetition(
                    savedWord,
                    repetitionCounter = 1,
                    nextRepetitionTime = nextTime
                )
            }
            SavedWord.Difficulty.MEDIUM -> {
                repetitionCounter++
                val toAdd = 60 * 60 * 1000 * (repetitionCounter.toDouble().pow(2).toLong())
                nextTime = currentTime + toAdd
                wordSetNewRepetition(savedWord, repetitionCounter, nextRepetitionTime = nextTime)
            }
            SavedWord.Difficulty.EASY -> {
                repetitionCounter++
                val toAdd = 60 * 60 * 1000 * (repetitionCounter.toDouble().pow(4).toLong())
                nextTime = currentTime + toAdd
                wordSetNewRepetition(savedWord, repetitionCounter, nextRepetitionTime = nextTime)
            }
        }

        savedWordsRepository.updateSavedWord(updatedSavedWord)
    }

    private fun wordSetNewRepetition(
        word: SavedWord,
        repetitionCounter: Int,
        nextRepetitionTime: Long
    ): SavedWord {
        return SavedWord(
            id = word.id,
            value = word.value,
            translation = word.translation,
            transcription = word.transcription,
            imageUrl = word.imageUrl,
            example = word.example,
            conjugation = word.conjugation,
            repetitionCounter = repetitionCounter,
            nextRepetitionTime = nextRepetitionTime,
            wordId = word.wordId,
            type = word.type
        )
    }
}