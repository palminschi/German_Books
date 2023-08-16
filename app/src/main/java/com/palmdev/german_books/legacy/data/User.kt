package com.palmdev.german_books.legacy.data

import android.content.Context

class User(private val context: Context) {
        fun getPremiumStatus(context: Context): Boolean {
            return SharedPref(context).get(SharedPref.PREMIUM_USER, false)
        }
        fun setPremiumStatus(value: Boolean){
            SharedPref(context).put(SharedPref.PREMIUM_USER, value)
        }
}