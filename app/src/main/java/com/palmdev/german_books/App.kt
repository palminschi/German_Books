package com.palmdev.german_books

import android.app.Application
import android.util.Log
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        // Init Ads
        MobileAds.initialize(this)

        // Init Firebase
        FirebaseApp.initializeApp(this)

        // ThreeTenBP - for Time, Date, Duration
        AndroidThreeTen.init(this)

        // Init Firebase Messaging
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task: Task<String> ->
                if (!task.isSuccessful) {
                    return@addOnCompleteListener
                }
                val token = task.result
                Log.d("TAG", "Token ->$token")
            }
    }

}