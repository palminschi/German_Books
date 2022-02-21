package com.palmdev.german_books.di

import com.palmdev.german_books.presentation.screens.book_reading.BookReadingViewModel
import com.palmdev.german_books.presentation.screens.dialog_translator_languages.TranslatorLanguagesViewModel
import com.palmdev.german_books.presentation.screens.home.HomeViewModel
import com.palmdev.german_books.presentation.screens.books.BooksViewModel
import com.palmdev.german_books.presentation.screens.dialog_save_word.SaveWordViewModel
import com.palmdev.german_books.presentation.screens.translator.TranslatorViewModel
import com.palmdev.german_books.presentation.screens.words.WordsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel { HomeViewModel() }

    viewModel {
        BooksViewModel(
            getBooksByTypeUseCase = get()
        )
    }

    viewModel {
        BookReadingViewModel(
            getBookContentUseCase = get(),
            saveReadingProgressUseCase = get(),
            getReadingProgressUseCase = get(),
            getTranslatorPreferencesUseCase = get()
        )
    }

    viewModel {
        TranslatorLanguagesViewModel(
            saveTranslatorPreferencesUseCase = get(),
            getUserLanguageUseCase = get(),
        )
    }

    viewModel {
        SaveWordViewModel()
    }

    viewModel {
        WordsViewModel()
    }

    viewModel {
        TranslatorViewModel()
    }


}