package com.palmdev.data.repository

import com.palmdev.data.storage.user.UserStorage
import com.palmdev.data.storage.user.model.LanguageEntity
import com.palmdev.domain.model.Language
import com.palmdev.domain.repository.UserRepository

class UserRepositoryImpl(private val userStorage: UserStorage): UserRepository {

    override fun saveUserLanguage(language: Language) {
        userStorage.saveLanguage(
            languageEntity = mapLangToData(language = language)
        )
    }

    override fun getUserLanguage(): Language {
        return mapLangToDomain(languageEntity = userStorage.getLanguage())
    }

    override fun saveTranslatorPreferences(language: Language) {
        userStorage.saveTranslatorPreferences(
            languageEntity = mapLangToData(language = language)
        )
    }

    override fun getTranslatorPreferences(): Language {
        return mapLangToDomain(
            languageEntity = userStorage.getTranslatorPreferences()
        )
    }


    // Mappers
    private fun mapLangToData(language: Language): LanguageEntity{
        return LanguageEntity(
            name = language.name,
            code = language.code
        )
    }

    private fun mapLangToDomain(languageEntity: LanguageEntity): Language{
        return Language(
            name = languageEntity.name,
            code = languageEntity.code
        )
    }
}