package com.palmdev.german_books.presentation.screens.words

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.palmdev.domain.model.GroupOfWords
import com.palmdev.domain.usecase.words.GetGroupsOfWordsUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class WordsViewModel(
    private val getGroupsOfWordsUseCase: GetGroupsOfWordsUseCase
) : ViewModel() {

    private val _groupsOfWords = MutableLiveData<List<GroupOfWords>>()
    val groupsOfWords: LiveData<List<GroupOfWords>> = _groupsOfWords

    init {
        viewModelScope.launch {
            getGroupsOfWordsUseCase.invoke().collect {
                _groupsOfWords.value = it
            }
        }
    }
}