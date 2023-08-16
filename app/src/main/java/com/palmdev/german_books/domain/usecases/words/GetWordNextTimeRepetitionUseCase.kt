package com.palmdev.german_books.domain.usecases.words

import com.palmdev.german_books.domain.model.SavedWord
import kotlin.math.abs
import kotlin.math.pow


class GetWordNextTimeRepetitionUseCase {
    operator fun invoke(word: SavedWord, difficulty: SavedWord.Difficulty, currentTimeMillis: Long): String {
        return when (difficulty) {
            SavedWord.Difficulty.DEFAULT -> "60 min"
            SavedWord.Difficulty.HARD -> "1 min"
            SavedWord.Difficulty.MEDIUM -> {
                val repetitionCounter = word.repetitionCounter + 1
                val toAdd = 60 * 60 * 1000 * (repetitionCounter.toDouble().pow(2).toLong())
                val nextTime = currentTimeMillis + toAdd
                formatTime(futureMillis = nextTime, currentTimeMillis)
            }
            SavedWord.Difficulty.EASY -> {
                val repetitionCounter = word.repetitionCounter + 1
                val toAdd = 60 * 60 * 1000 * (repetitionCounter.toDouble().pow(4).toLong())
                val nextTime = currentTimeMillis + toAdd
                formatTime(futureMillis = nextTime, currentTimeMillis)
            }
        }
    }

    private fun formatTime(futureMillis: Long, currentTimeMillis: Long): String {
        val remainingMillis = abs(futureMillis - currentTimeMillis)
        val seconds = (remainingMillis / 1000).toInt()
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24

        return when {
            minutes in 1 until 60 -> "$minutes min"
            hours in 1 until 24 -> "$hours h"
            days in 1 until 31 -> "$days day"
            days > 365 -> "${days / 365} year"
            days > 30 -> "${days / 30} month"
            else -> ""
        }
    }

}