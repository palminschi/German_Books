package com.palmdev.german_books.domain.usecases.words

import com.palmdev.german_books.domain.repository.SavedWordsRepository
import javax.inject.Inject

class GetSavedWordsCountUseCase @Inject constructor(
    private val savedWordsRepository: SavedWordsRepository
) {
    suspend operator fun invoke(): Int {
        return savedWordsRepository.getWordsCount()
    }
}