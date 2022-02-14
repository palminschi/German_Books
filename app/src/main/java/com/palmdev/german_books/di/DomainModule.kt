package com.palmdev.german_books.di

import com.palmdev.domain.usecase.books.GetBookContentUseCase
import org.koin.dsl.module

val domainModule = module {

    factory<GetBookContentUseCase> {
        GetBookContentUseCase(booksContentRepository = get())
    }

}