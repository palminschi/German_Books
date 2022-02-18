package com.palmdev.german_books.di

import com.palmdev.data.repository.BooksContentRepositoryImpl
import com.palmdev.data.repository.UserRepositoryImpl
import com.palmdev.data.storage.books.AssetsBooksContentStorage
import com.palmdev.data.storage.books.BooksContentStorage
import com.palmdev.data.storage.user.SharedPrefsUserStorage
import com.palmdev.data.storage.user.UserStorage
import com.palmdev.domain.repository.BooksContentRepository
import com.palmdev.domain.repository.UserRepository
import org.koin.dsl.module

val dataModule = module {

    // Books
    single<BooksContentStorage> {
        AssetsBooksContentStorage(context = get())
    }
    single<BooksContentRepository> {
        BooksContentRepositoryImpl(booksContentStorage = get())
    }

    // User
    single<UserStorage> {
        SharedPrefsUserStorage(context = get())
    }
    single<UserRepository> {
        UserRepositoryImpl(userStorage = get())
    }

}