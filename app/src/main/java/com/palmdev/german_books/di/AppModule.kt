package com.palmdev.german_books.di

import com.palmdev.german_books.presentation.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel<MainViewModel> {
        MainViewModel(
            getBookContentUseCase = get()
        )
    }
}