package com.palmdev.german_books

import android.app.Application
import com.palmdev.german_books.di.presentationModule
import com.palmdev.german_books.di.databaseModule
import com.palmdev.german_books.di.dataModule
import com.palmdev.german_books.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(listOf(dataModule, domainModule, presentationModule, databaseModule))
        }
    }

}