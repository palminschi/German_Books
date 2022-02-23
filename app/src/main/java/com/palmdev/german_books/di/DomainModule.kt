package com.palmdev.german_books.di

import com.palmdev.domain.usecase.books.*
import com.palmdev.domain.usecase.user.GetTranslatorPreferencesUseCase
import com.palmdev.domain.usecase.user.GetUserLanguageUseCase
import com.palmdev.domain.usecase.user.SaveTranslatorPreferencesUseCase
import com.palmdev.domain.usecase.user.SaveUserLanguageUseCase
import com.palmdev.domain.usecase.words.AddWordUseCase
import com.palmdev.domain.usecase.words.GetAllWordsUseCase
import com.palmdev.domain.usecase.words.GetGroupsOfWordsUseCase
import com.palmdev.domain.usecase.words.GetWordsByGroupUseCase
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
    factory {
        GetBooksByTypeUseCase(booksRepository = get())
    }
    factory {
        GetLastBookReadUseCase(booksRepository = get())
    }
    factory {
        SaveLastBookReadUseCase(booksRepository = get())
    }
    factory {
        SetBookFavoriteStatusUseCase(booksRepository = get())
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

    // Words
    factory {
        GetAllWordsUseCase(wordsRepository = get())
    }
    factory {
        AddWordUseCase(wordsRepository = get())
    }
    factory {
        GetGroupsOfWordsUseCase(wordsRepository = get())
    }
    factory {
        GetWordsByGroupUseCase(wordsRepository = get())
    }

}