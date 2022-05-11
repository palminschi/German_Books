package com.palmdev.german_books.di

import com.palmdev.domain.usecase.books.*
import com.palmdev.domain.usecase.purchases.GetPremiumStatusUseCase
import com.palmdev.domain.usecase.purchases.SetPremiumStatusUseCase
import com.palmdev.domain.usecase.user.*
import com.palmdev.domain.usecase.words.*
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
    factory {
        GetBookByIdUseCase(booksRepository = get())
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
    factory {
        HasUserRatedAppUseCase(userRepository = get())
    }
    factory {
        SetAppIsRatedUseCase(userRepository = get())
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
    factory {
        DeleteByGroupUseCase(wordsRepository = get())
    }

    // Purchase
    factory {
        GetPremiumStatusUseCase(purchasesRepository = get())
    }
    factory {
        SetPremiumStatusUseCase(purchasesRepository = get())
    }

}