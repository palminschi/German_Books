package com.palmdev.data.storage.user

import com.palmdev.data.storage.user.model.LanguageEntity

interface UserStorage {

    fun saveLanguage(languageEntity: LanguageEntity)

    fun getLanguage(): LanguageEntity

    fun saveTranslatorPreferences(languageEntity: LanguageEntity)

    fun getTranslatorPreferences(): LanguageEntity

    fun hasUserRatedApp(): Boolean

    fun setAppIsRated(boolean: Boolean)

}