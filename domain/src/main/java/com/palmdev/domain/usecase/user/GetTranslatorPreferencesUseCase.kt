package com.palmdev.domain.usecase.user

import com.palmdev.domain.model.Language
import com.palmdev.domain.repository.UserRepository

class GetTranslatorPreferencesUseCase(private val userRepository: UserRepository) {

    fun execute(): Language {
        return userRepository.getTranslatorPreferences()
    }

}