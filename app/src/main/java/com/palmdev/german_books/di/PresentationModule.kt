package com.palmdev.german_books.di

import com.palmdev.german_books.presentation.screens.book_reading.BookReadingViewModel
import com.palmdev.german_books.presentation.screens.book_reading.bottom_sheet.ReadingBottomSheetViewModel
import com.palmdev.german_books.presentation.screens.books.BooksViewModel
import com.palmdev.german_books.presentation.screens.dialog_save_word.SaveWordViewModel
import com.palmdev.german_books.presentation.screens.dialog_translator_languages.TranslatorLanguagesViewModel
import com.palmdev.german_books.presentation.screens.group_of_words.GroupOfWordsViewModel
import com.palmdev.german_books.presentation.screens.home.HomeViewModel
import com.palmdev.german_books.presentation.screens.shop.ShopViewModel
import com.palmdev.german_books.presentation.screens.translator.TranslatorViewModel
import com.palmdev.german_books.presentation.screens.words.WordsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    viewModel {
        HomeViewModel(
            getLastBookReadUseCase = get(),
            getAllWordsUseCase = get(),
            setAppIsRatedUseCase = get(),
            hasUserRatedAppUseCase = get(),
            getReadingProgressUseCase = get(),
            getBookByIdUseCase = get(),
            getPremiumStatusUseCase = get()
        )
    }

    viewModel {
        BooksViewModel(
            getBooksByTypeUseCase = get(),
            getPremiumStatusUseCase = get()
        )
    }

    viewModel {
        BookReadingViewModel(
            getBookContentUseCase = get(),
            saveReadingProgressUseCase = get(),
            getReadingProgressUseCase = get(),
            getTranslatorPreferencesUseCase = get(),
            saveLastBookReadUseCase = get(),
            getPremiumStatusUseCase = get()
        )
    }

    viewModel {
        TranslatorLanguagesViewModel(
            saveTranslatorPreferencesUseCase = get(),
            getTranslatorPreferencesUseCase = get(),
            getUserLanguageUseCase = get(),
        )
    }

    viewModel {
        SaveWordViewModel(
            addWordUseCase = get(),
            getAllWordsUseCase = get(),
            getPremiumStatusUseCase = get()
        )
    }

    viewModel {
        WordsViewModel(
            getGroupsOfWordsUseCase = get()
        )
    }

    viewModel {
        GroupOfWordsViewModel(
            getWordsByGroupUseCase = get(),
            getPremiumStatusUseCase = get(),
            deleteByGroupUseCase = get()
        )
    }

    viewModel {
        TranslatorViewModel(
            getTranslatorPreferencesUseCase = get()
        )
    }

    viewModel {
        ReadingBottomSheetViewModel(
            setBookFavoriteStatusUseCase = get(),
            getAllWordsUseCase = get(),
            getTranslatorPreferencesUseCase = get(),
            getBookByIdUseCase = get()
        )
    }

    viewModel {
        ShopViewModel(
            getPremiumStatusUseCase = get()
        )
    }

}