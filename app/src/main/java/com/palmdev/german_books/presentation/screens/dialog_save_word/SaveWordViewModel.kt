package com.palmdev.german_books.presentation.screens.dialog_save_word

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.palmdev.domain.model.Word
import com.palmdev.domain.usecase.purchases.GetPremiumStatusUseCase
import com.palmdev.domain.usecase.words.AddWordUseCase
import com.palmdev.domain.usecase.words.GetAllWordsUseCase
import kotlinx.coroutines.launch

class SaveWordViewModel(
    private val addWordUseCase: AddWordUseCase,
    private val getAllWordsUseCase: GetAllWordsUseCase,
    private val getPremiumStatusUseCase: GetPremiumStatusUseCase
) : ViewModel() {

    private val _words = MutableLiveData<List<Word>>()
    val words: LiveData<List<Word>> = _words

    private val _userPremiumStatus = MutableLiveData<Boolean>()
    val userPremiumStatus: LiveData<Boolean> = _userPremiumStatus

    init {
        _userPremiumStatus.value = getPremiumStatusUseCase.execute()
    }

    fun initWords() {
        viewModelScope.launch {
            getAllWordsUseCase.invoke().collect {
                _words.value = it
            }
        }
    }

    fun addWord(word: String, translation: String, lastWord: Word?) {
        viewModelScope.launch {
            addWordUseCase.invoke(
                word = word,
                translation = translation,
                lastWord = lastWord
            )
        }
    }
}