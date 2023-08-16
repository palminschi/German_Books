package com.palmdev.german_books.data.storage

import android.content.Context
import com.palmdev.german_books.domain.model.Language
import com.palmdev.german_books.utils.Languages
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

private const val KEY_APP_RATED = "APP_IS_RATED"
private const val KEY_LAST_SESSION_DATE = "KEY_LAST_SESSION_DATE"
private const val KEY_HAS_PREMIUM = "USER_PREMIUM_STATUS"
private const val KEY_DARK_MODE = "KEY_DARK_MODE"
private const val KEY_FONT_SIZE = "KEY_FONT_SIZE"
private const val KEY_USER_LANG_NAME = "TRANSLATOR_LANGUAGE_NAME"
private const val KEY_USER_LANG_CODE = "TRANSLATOR_LANGUAGE_CODE"
private const val KEY_USER_LANG_IMG = "KEY_USER_LANG_IMG"
private const val KEY_FIRST_OPEN = "KEY_FIRST_OPEN"

class UserStorage @Inject constructor(context: Context) {

    private val prefs = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)

    val hasPremium get() = prefs.getBoolean(KEY_HAS_PREMIUM, false)

    fun setPremiumStatus(boolean: Boolean) {
        prefs.edit().putBoolean(KEY_HAS_PREMIUM, boolean).apply()
    }

    val hasRatedApp get() = prefs.getBoolean(KEY_APP_RATED, false)

    fun isFirstSessionToday(): Boolean {
        val lastSessionDate = prefs.getString(KEY_LAST_SESSION_DATE, null)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDate = dateFormat.format(Date())

        // If it's the first session today, update the last session date
        if (lastSessionDate == null || lastSessionDate != currentDate) {
            val editor = prefs.edit()
            editor.putString(KEY_LAST_SESSION_DATE, currentDate)
            editor.apply()
            return true
        }

        return false
    }

    fun setUserRatedApp() = prefs.edit().putBoolean(KEY_APP_RATED, true).apply()

    val isDarkMode get() = prefs.getBoolean(KEY_DARK_MODE, true)

    val fontSize get() = prefs.getFloat(KEY_FONT_SIZE, 17f)

    fun switchDarkMode() = prefs.edit().putBoolean(KEY_DARK_MODE, !isDarkMode).apply()

    fun setFontSize(float: Float) = prefs.edit().putFloat(KEY_FONT_SIZE, float).apply()

    fun setUserLanguage(language: Language) {
        prefs.edit().putString(KEY_USER_LANG_NAME, language.name).apply()
        prefs.edit().putString(KEY_USER_LANG_CODE, language.code).apply()
        prefs.edit().putString(KEY_USER_LANG_IMG, language.imageUrl).apply()

    }

    val userLanguage: Language
        get() {
            return Language(
                name = prefs.getString(KEY_USER_LANG_NAME, Languages.EN.name) ?: Languages.EN.name,
                code = prefs.getString(KEY_USER_LANG_CODE, Languages.EN.code) ?: Languages.EN.code,
                imageUrl = prefs.getString(KEY_USER_LANG_IMG, null)
            )
        }

    val isFirstOpen: Boolean get() = prefs.getBoolean(KEY_FIRST_OPEN, true)
    fun setIsFirstOpen(boolean: Boolean) {
        prefs.edit().putBoolean(KEY_FIRST_OPEN, boolean).apply()
    }
}