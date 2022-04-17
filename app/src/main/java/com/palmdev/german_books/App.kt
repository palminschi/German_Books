package com.palmdev.german_books

import android.app.Application
import android.util.Log
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.palmdev.german_books.di.presentationModule
import com.palmdev.german_books.di.databaseModule
import com.palmdev.german_books.di.dataModule
import com.palmdev.german_books.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        // Koin init
        startKoin {
            androidContext(this@App)
            modules(listOf(dataModule, domainModule, presentationModule, databaseModule))
        }

        // Firebase init
        FirebaseApp.initializeApp(this)
        // Firebase Messaging init
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task: Task<String> ->
                if (!task.isSuccessful) {
                    return@addOnCompleteListener
                }
                val token = task.result
                Log.d("TAG", "Token ->$token")
            }
        // Admob init
        MobileAds.initialize(this)
    }

}