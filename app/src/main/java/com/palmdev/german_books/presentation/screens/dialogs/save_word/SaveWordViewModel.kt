package com.palmdev.german_books.presentation.screens.dialogs.save_word

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.palmdev.german_books.domain.model.Word
import com.palmdev.german_books.domain.repository.UserRepository
import com.palmdev.german_books.domain.usecases.words.SaveNewWordsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SaveWordViewModel @Inject constructor(
    private val saveNewWordsUseCase: SaveNewWordsUseCase,
    private val userRepository: UserRepository
) : ViewModel() {

    val userLanguage = userRepository.userLanguage

    fun saveWord(word: String, translation: String) {
        viewModelScope.launch(Dispatchers.IO) {
            saveNewWordsUseCase(
                listOf(
                    Word(
                        wordId = word,
                        value = word,
                        translation = translation
                    )
                )
            )
        }
    }
}