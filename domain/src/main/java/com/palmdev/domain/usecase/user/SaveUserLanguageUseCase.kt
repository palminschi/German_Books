package com.palmdev.domain.usecase.user

import com.palmdev.domain.model.Language
import com.palmdev.domain.repository.UserRepository

class SaveUserLanguageUseCase(private val userRepository: UserRepository) {

    fun execute(language: Language){
        userRepository.saveUserLanguage(language = language)
    }

}