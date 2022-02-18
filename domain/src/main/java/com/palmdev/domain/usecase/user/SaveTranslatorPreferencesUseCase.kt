package com.palmdev.domain.usecase.user

import com.palmdev.domain.model.Language
import com.palmdev.domain.repository.UserRepository

class SaveTranslatorPreferencesUseCase(private val userRepository: UserRepository) {

    fun execute(language: Language){
        userRepository.saveTranslatorPreferences(language = language)
    }

}