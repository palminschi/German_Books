package com.palmdev.german_books.presentation.screens.book_reading

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.palmdev.domain.model.BookReadingProgress
import com.palmdev.domain.model.Language
import com.palmdev.domain.usecase.books.GetBookContentUseCase
import com.palmdev.domain.usecase.books.GetReadingProgressUseCase
import com.palmdev.domain.usecase.books.SaveLastBookReadUseCase
import com.palmdev.domain.usecase.books.SaveReadingProgressUseCase
import com.palmdev.domain.usecase.purchases.GetPremiumStatusUseCase
import com.palmdev.domain.usecase.user.GetTranslatorPreferencesUseCase

class BookReadingViewModel(
    private val getBookContentUseCase: GetBookContentUseCase,
    private val saveReadingProgressUseCase: SaveReadingProgressUseCase,
    private val getReadingProgressUseCase: GetReadingProgressUseCase,
    private val getTranslatorPreferencesUseCase: GetTranslatorPreferencesUseCase,
    private val saveLastBookReadUseCase: SaveLastBookReadUseCase,
    private val getPremiumStatusUseCase: GetPremiumStatusUseCase
) : ViewModel() {

    private val _bookContent = MutableLiveData<String>()
    private val _currentPage = MutableLiveData<Int>()
    private val _translatorPreferences = MutableLiveData<Language>()
    private val _userPremiumStatus = MutableLiveData<Boolean>()

    val bookContent: LiveData<String> = _bookContent
    val currentPage: LiveData<Int> = _currentPage
    val translatorPreferences: LiveData<Language> = _translatorPreferences
    val userPremiumStatus: LiveData<Boolean> = _userPremiumStatus

    init {
        _translatorPreferences.value = getTranslatorPreferencesUseCase.execute()
        _userPremiumStatus.value = getPremiumStatusUseCase.execute()
    }

    fun initBook(id: Int) {
        _bookContent.value = getBookContentUseCase.execute(id).content
    }

    fun saveReadingProgress(bookId: Int, currentPage: Int, totalPages: Int) {
        val params = BookReadingProgress(
            bookId = bookId,
            currentPage = currentPage,
            totalPages = totalPages
        )
        saveReadingProgressUseCase.execute(readingProgress = params)
    }

    fun initCurrentPage(bookId: Int){
        _currentPage.value = getReadingProgressUseCase.execute(bookId = bookId).currentPage
    }

    fun saveLastBookRead(bookId: Int){
        saveLastBookReadUseCase.execute(bookId = bookId)
    }

}