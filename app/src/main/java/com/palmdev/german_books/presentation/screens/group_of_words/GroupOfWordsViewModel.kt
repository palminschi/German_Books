package com.palmdev.german_books.presentation.screens.group_of_words

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.palmdev.domain.model.Word
import com.palmdev.domain.usecase.words.GetWordsByGroupUseCase
import kotlinx.coroutines.launch

class GroupOfWordsViewModel(
    private val getWordsByGroupUseCase: GetWordsByGroupUseCase
) : ViewModel() {

    private val _words = MutableLiveData<List<Word>>()
    val words: LiveData<List<Word>> = _words

    fun getWords(groupId: Int) {
        if (_words.value.isNullOrEmpty()) {
            viewModelScope.launch {
                getWordsByGroupUseCase.invoke(groupId = groupId).collect {
                    _words.value = it
                }
            }
        }
    }
}