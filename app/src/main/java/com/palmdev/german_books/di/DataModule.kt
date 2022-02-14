package com.palmdev.german_books.di

import com.palmdev.data.repository.BooksContentRepository_Impl
import com.palmdev.data.storage.AssetsBooksContentStorage
import com.palmdev.data.storage.BooksContentStorage
import com.palmdev.domain.repository.BooksContentRepository
import org.koin.dsl.module

val dataModule = module {

    // Books
    single<BooksContentStorage>{
        AssetsBooksContentStorage(context = get())
    }

    single<BooksContentRepository>{
        BooksContentRepository_Impl(booksContentStorage = get())
    }

}