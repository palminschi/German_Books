package com.palmdev.german_books.presentation.screens.book_reading

import android.app.Dialog
import android.util.Log
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.palmdev.domain.model.BookContent
import com.palmdev.domain.model.BookReadingProgress
import com.palmdev.domain.model.Language
import com.palmdev.domain.usecase.books.GetBookContentUseCase
import com.palmdev.domain.usecase.books.GetReadingProgressUseCase
import com.palmdev.domain.usecase.books.SaveReadingProgressUseCase
import com.palmdev.domain.usecase.user.GetTranslatorPreferencesUseCase
import com.palmdev.german_books.utils.GoogleMLKitTranslator

class BookReadingViewModel(
    private val getBookContentUseCase: GetBookContentUseCase,
    private val saveReadingProgressUseCase: SaveReadingProgressUseCase,
    private val getReadingProgressUseCase: GetReadingProgressUseCase,
    private val getTranslatorPreferencesUseCase: GetTranslatorPreferencesUseCase
) : ViewModel() {

    private val _bookContent = MutableLiveData<String>()
    private val _currentPage = MutableLiveData<Int>()
    private val _translatorPreferences = MutableLiveData<Language>()

    val bookContent: LiveData<String> = _bookContent
    val currentPage: LiveData<Int> = _currentPage
    val translatorPreferences: LiveData<Language> = _translatorPreferences

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

    fun initTranslatorPreferences(){
        _translatorPreferences.value = getTranslatorPreferencesUseCase.execute()
    }

}