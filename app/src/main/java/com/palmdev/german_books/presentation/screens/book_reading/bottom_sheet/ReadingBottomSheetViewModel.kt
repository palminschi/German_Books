package com.palmdev.german_books.presentation.screens.book_reading.bottom_sheet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.palmdev.domain.model.Language
import com.palmdev.domain.usecase.books.GetBookByIdUseCase
import com.palmdev.domain.usecase.books.SetBookFavoriteStatusUseCase
import com.palmdev.domain.usecase.user.GetTranslatorPreferencesUseCase
import com.palmdev.domain.usecase.words.GetAllWordsUseCase
import kotlinx.coroutines.launch

class ReadingBottomSheetViewModel(
    private val setBookFavoriteStatusUseCase: SetBookFavoriteStatusUseCase,
    private val getAllWordsUseCase: GetAllWordsUseCase,
    private val getTranslatorPreferencesUseCase: GetTranslatorPreferencesUseCase,
    private val getBookByIdUseCase: GetBookByIdUseCase
) : ViewModel() {

    private val _translatorLanguage = MutableLiveData<Language>()
    val translatorLanguage: LiveData<Language> = _translatorLanguage

    private val _numberOfSavedWords = MutableLiveData<Int>()
    val numberOfSavedWords: LiveData<Int> = _numberOfSavedWords

    private val _favoriteStatus = MutableLiveData<Boolean>()
    val favoriteStatus: LiveData<Boolean> = _favoriteStatus

    init {
        _translatorLanguage.value = getTranslatorPreferencesUseCase.execute()

        viewModelScope.launch {
            getAllWordsUseCase.invoke().collect {
                _numberOfSavedWords.value = it.size
            }
        }
    }

    fun getFavoriteStatus(bookId: Int) {
        _favoriteStatus.value = getBookByIdUseCase.execute(bookId).favorite
    }

    fun setFavoriteStatus(bookId: Int, status: Boolean) {
        setBookFavoriteStatusUseCase.execute(bookId = bookId, status = status)
    }
}