package com.palmdev.domain.repository

import com.palmdev.domain.model.Language

interface UserRepository {

    fun saveUserLanguage(language: Language)

    fun getUserLanguage(): Language

    fun saveTranslatorPreferences(language: Language)

    fun getTranslatorPreferences(): Language

    fun hasUserRatedApp(): Boolean

    fun setAppIsRated(boolean: Boolean)

}