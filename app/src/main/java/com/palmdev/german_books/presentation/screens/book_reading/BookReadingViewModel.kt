package com.palmdev.german_books.presentation.screens.book_reading

import androidx.lifecycle.ViewModel
import com.palmdev.domain.model.BookContent
import com.palmdev.domain.usecase.books.GetBookContentUseCase

class BookReadingViewModel(private val getBookContentUseCase: GetBookContentUseCase
) : ViewModel() {

    fun getBook(id: Int): BookContent {
        return getBookContentUseCase.execute(id)
    }

}