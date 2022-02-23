package com.palmdev.german_books.presentation.screens.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.palmdev.domain.model.GroupOfWords
import com.palmdev.domain.model.Word
import com.palmdev.domain.usecase.words.AddWordUseCase
import com.palmdev.domain.usecase.words.GetAllWordsUseCase
import com.palmdev.domain.usecase.words.GetGroupsOfWordsUseCase
import com.palmdev.domain.usecase.words.GetWordsByGroupUseCase
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getAllWordsUseCase: GetAllWordsUseCase,
    private val addWordUseCase: AddWordUseCase,
    private val getGroupsOfWordsUseCase: GetGroupsOfWordsUseCase,
    private val getWordsByGroupUseCase: GetWordsByGroupUseCase
) : ViewModel() {
    // TODO: Implement the ViewModel


    private val _words = MutableLiveData<List<Word>>()
    val words: LiveData<List<Word>> = _words

    private val _groups = MutableLiveData<List<GroupOfWords>>()
    val groups: LiveData<List<GroupOfWords>> = _groups

    val remoteWords = arrayListOf<Word>()

    fun addWord() {
        viewModelScope.launch {
            addWordUseCase.invoke(
                word = "Hello",
                translation = "Privet",
                sentence = "Hello World"
            )
        }
    }

    fun getGroups() {
        viewModelScope.launch {
            getGroupsOfWordsUseCase.invoke().collect {
                _groups.value = it
            }
        }
    }

    fun getWords() {
        viewModelScope.launch {
            getWordsByGroupUseCase.invoke(0).collect {

                _words.value = it

            }
        }
    }
}