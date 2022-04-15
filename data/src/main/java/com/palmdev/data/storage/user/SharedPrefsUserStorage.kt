package com.palmdev.data.storage.user

import android.content.Context
import com.palmdev.data.storage.user.model.LanguageEntity
import com.palmdev.data.util.Constants

class SharedPrefsUserStorage(context: Context): UserStorage {

    private val sharedPrefs = context.getSharedPreferences(Constants.SHARED_PREFS_NAME, Context.MODE_PRIVATE)

    override fun saveLanguage(languageEntity: LanguageEntity) {
        // Language Name
        sharedPrefs.edit().putString(
            Constants.USER_LANGUAGE_NAME, languageEntity.name
        ).apply()
        // Language Code
        sharedPrefs.edit().putString(
            Constants.USER_LANGUAGE_CODE, languageEntity.code
        ).apply()
    }

    override fun getLanguage(): LanguageEntity {

        val langName = sharedPrefs.getString(
            Constants.USER_LANGUAGE_NAME, Constants.SHARED_PREFS_NO_DATA
        ) ?: Constants.SHARED_PREFS_NO_DATA

        val langCode = sharedPrefs.getString(
            Constants.USER_LANGUAGE_CODE, Constants.SHARED_PREFS_NO_DATA
        ) ?: Constants.SHARED_PREFS_NO_DATA

        return LanguageEntity(
            name = langName,
            code = langCode
        )
    }

    override fun saveTranslatorPreferences(languageEntity: LanguageEntity) {
        // Language Name
        sharedPrefs.edit().putString(
            Constants.TRANSLATOR_LANGUAGE_NAME, languageEntity.name
        ).apply()
        // Language Code
        sharedPrefs.edit().putString(
            Constants.TRANSLATOR_LANGUAGE_CODE, languageEntity.code
        ).apply()
    }

    override fun getTranslatorPreferences(): LanguageEntity {
        val langName = sharedPrefs.getString(
            Constants.TRANSLATOR_LANGUAGE_NAME, Constants.SHARED_PREFS_NO_DATA
        ) ?: Constants.SHARED_PREFS_NO_DATA

        val langCode = sharedPrefs.getString(
            Constants.TRANSLATOR_LANGUAGE_CODE, Constants.SHARED_PREFS_NO_DATA
        ) ?: Constants.SHARED_PREFS_NO_DATA

        return LanguageEntity(
            name = langName,
            code = langCode
        )
    }

    override fun hasUserRatedApp(): Boolean {
        return sharedPrefs.getBoolean(Constants.APP_IS_RATED, false)
    }

    override fun setAppIsRated(boolean: Boolean) {
        sharedPrefs.edit().putBoolean(Constants.APP_IS_RATED, boolean).apply()
    }

}