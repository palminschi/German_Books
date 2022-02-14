package com.palmdev.german_books.di

import com.palmdev.german_books.presentation.screens.book_reading.BookReadingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel<BookReadingViewModel> {
        BookReadingViewModel(
            getBookContentUseCase = get()
        )
    }
}