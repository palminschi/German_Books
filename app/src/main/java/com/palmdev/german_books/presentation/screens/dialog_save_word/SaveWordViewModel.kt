package com.palmdev.german_books.presentation.screens.dialog_save_word

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.palmdev.domain.usecase.words.AddWordUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SaveWordViewModel(
    private val addWordUseCase: AddWordUseCase
) : ViewModel() {

    fun addWord(
        word: String,
        translation: String,
        sentence: String?
    ) {
        viewModelScope.launch {
            addWordUseCase.invoke(
                word = word,
                translation = translation,
                sentence = sentence
            )
        }
    }
}