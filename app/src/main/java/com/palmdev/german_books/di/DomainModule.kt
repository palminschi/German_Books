package com.palmdev.german_books.di

import com.palmdev.domain.usecase.books.GetBookContentUseCase
import com.palmdev.domain.usecase.books.GetReadingProgressUseCase
import com.palmdev.domain.usecase.books.SaveReadingProgressUseCase
import com.palmdev.domain.usecase.user.GetTranslatorPreferencesUseCase
import com.palmdev.domain.usecase.user.GetUserLanguageUseCase
import com.palmdev.domain.usecase.user.SaveTranslatorPreferencesUseCase
import com.palmdev.domain.usecase.user.SaveUserLanguageUseCase
import org.koin.dsl.module

val domainModule = module {

    // Books
    factory {
        GetBookContentUseCase(booksContentRepository = get())
    }
    factory {
        SaveReadingProgressUseCase(booksContentRepository = get())
    }
    factory {
        GetReadingProgressUseCase(booksContentRepository = get())
    }

    // User
    factory {
        SaveUserLanguageUseCase(userRepository = get())
    }
    factory {
        GetUserLanguageUseCase(userRepository = get())
    }
    factory {
        SaveTranslatorPreferencesUseCase(userRepository = get())
    }
    factory {
        GetTranslatorPreferencesUseCase(userRepository = get())
    }

}