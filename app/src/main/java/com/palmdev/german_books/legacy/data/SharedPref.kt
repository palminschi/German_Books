package com.palmdev.german_books.legacy.data

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.palmdev.german_books.utils.TinyDB


class SharedPref(private val context: Context) {
    private var mSharedPref: SharedPreferences? = null

    companion object {
        const val FIRST_OPEN = "FIRST_OPEN" // Boolean
        const val USER_NAME = "USER_NAME" // String
        const val USER_LEVEL = "USER_LEVEL" // String "A1","A2",...
        const val USER_GENDER = "USER_GENDER" // String "Man" or "Woman"
        const val USER_LANGUAGE_NAME = "USER_LANGUAGE_NAME" // String "English",...
        const val USER_TRANSLATOR_LANGUAGE_CODE = "USER_TRANSLATOR_LANGUAGE_CODE" // String "en", "ru",...
        const val FAVORITE_BOOKS = "FAVORITE_BOOKS" // Int
        const val LEARNED_TOPICS = "LEARNED_TOPICS" // Int
        const val EXAMS_PASSED = "EXAMS_PASSED" // Int
        const val PREMIUM_USER = "USER_PREMIUM_STATUS" // Boolean
        const val WORDS_ARRAY = "WORDS_ARRAY" // ArrayList<String>
        const val TRANSLATED_WORDS_ARRAY = "TRANSLATED_WORDS_ARRAY" // ArrayList<String>
        const val PHRASES_ARRAY = "PHRASES_ARRAY" // ArrayList<String>
        const val QUICK_TEST_COUNTER = "QUICK_TEST_COUNTER"
    }


    init {
        if (mSharedPref == null) mSharedPref =
            context.getSharedPreferences(context.packageName, Activity.MODE_PRIVATE)
    }

    fun get(key: String?, defValue: String?): String? {
        return mSharedPref?.getString(key, defValue)
    }

    fun put(key: String?, value: String?) {
        val prefsEditor = mSharedPref!!.edit()
        prefsEditor.putString(key, value)
        prefsEditor.apply()
    }

    fun get(key: String?, defValue: Boolean): Boolean {
        return mSharedPref?.getBoolean(key, defValue) ?: false
    }

    fun put(key: String?, value: Boolean) {
        val prefsEditor = mSharedPref?.edit()
        prefsEditor?.putBoolean(key, value)
        prefsEditor?.apply()
    }

    fun get(key: String?, defValue: Int): Int {
        return mSharedPref?.getInt(key, defValue) ?: 0
    }

    fun put(key: String?, value: Int?) {
        val prefsEditor = mSharedPref!!.edit()
        prefsEditor.putInt(key, value!!)
            .apply()
    }


    fun getArray(key: String?): ArrayList<String> {
        val tinyDB = TinyDB(context)
        return tinyDB.getListString(key)
    }

}