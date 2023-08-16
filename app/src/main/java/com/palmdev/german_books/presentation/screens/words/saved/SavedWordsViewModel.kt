package com.palmdev.german_books.presentation.screens.words.saved

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.palmdev.german_books.domain.model.SavedWord
import com.palmdev.german_books.domain.repository.SavedWordsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedWordsViewModel @Inject constructor(private val savedWordsRepository: SavedWordsRepository) :
    ViewModel() {

    val savedWords = MutableLiveData<List<SavedWord>>()

    fun initWords() {
        viewModelScope.launch(Dispatchers.IO) {
            savedWordsRepository.getAllSavedWords().collect {
                savedWords.postValue(it)
            }
        }
    }

    fun deleteWord(savedWord: SavedWord) {
        viewModelScope.launch(Dispatchers.IO) {
            savedWordsRepository.deleteWord(savedWord)
            initWords()
        }
    }
}