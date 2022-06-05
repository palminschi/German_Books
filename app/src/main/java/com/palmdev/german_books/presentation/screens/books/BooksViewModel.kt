package com.palmdev.german_books.presentation.screens.books

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.palmdev.domain.model.Book
import com.palmdev.domain.model.BooksType
import com.palmdev.domain.usecase.books.GetBooksByTypeUseCase
import com.palmdev.domain.usecase.purchases.GetPremiumStatusUseCase

class BooksViewModel(
    private val getBooksByTypeUseCase: GetBooksByTypeUseCase,
    private val getPremiumStatusUseCase: GetPremiumStatusUseCase
) : ViewModel() {

    private val _books = MutableLiveData<List<Book>>()
    val books: LiveData<List<Book>> = _books

    private val _premiumStatus = MutableLiveData<Boolean>()
    val premiumStatus: LiveData<Boolean> = _premiumStatus

    init {
        _premiumStatus.value = getPremiumStatusUseCase.execute()
    }

    fun initAllBooks() {
        _books.value = getBooksByTypeUseCase.execute(type = BooksType.ALL)
    }

    fun initEasyBooks() {
        _books.value = getBooksByTypeUseCase.execute(type = BooksType.EASY)
    }

    fun initMediumBooks() {
        _books.value = getBooksByTypeUseCase.execute(type = BooksType.MEDIUM)
    }

    fun initHardBooks() {
        _books.value = getBooksByTypeUseCase.execute(type = BooksType.HARD)
    }

    fun initFavoriteBooks() {
        _books.value = getBooksByTypeUseCase.execute(type = BooksType.FAVORITES)
    }

}