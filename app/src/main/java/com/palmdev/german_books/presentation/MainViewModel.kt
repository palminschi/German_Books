package com.palmdev.german_books.presentation

import androidx.lifecycle.ViewModel
import com.palmdev.domain.model.BookContent
import com.palmdev.domain.usecase.books.GetBookContentUseCase

class MainViewModel(
    private val getBookContentUseCase: GetBookContentUseCase
) : ViewModel() {

    fun getBook(id: Int): BookContent {
        return getBookContentUseCase.execute(id)
    }

}