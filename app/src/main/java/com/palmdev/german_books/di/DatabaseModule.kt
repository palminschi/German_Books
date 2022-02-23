package com.palmdev.german_books.di

import android.app.Application
import androidx.room.Room
import com.palmdev.data.database.WordsDao
import com.palmdev.data.database.WordsDatabase
import com.palmdev.data.repository.WordsRepositoryImpl
import com.palmdev.data.util.Constants
import com.palmdev.domain.repository.WordsRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {

    fun provideDatabase(application: Application): WordsDatabase {
        return Room.databaseBuilder(
            application,
            WordsDatabase::class.java,
            Constants.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideDao(database: WordsDatabase): WordsDao {
        return database.wordsDao()
    }

    single {
        provideDatabase(
            application = androidApplication()
        )
    }

    single {
        provideDao( database = get() )
    }

    single<WordsRepository> {
        WordsRepositoryImpl(wordsDao = get())
    }
}