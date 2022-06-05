package com.palmdev.german_books.utils

import android.content.Context
import android.net.ConnectivityManager
import androidx.core.content.ContextCompat.getSystemService


object Network {
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.applicationContext
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}