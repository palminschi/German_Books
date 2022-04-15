package com.palmdev.domain.usecase.user

import com.palmdev.domain.repository.UserRepository

class HasUserRatedAppUseCase(private val userRepository: UserRepository) {

    fun execute(): Boolean {
        return userRepository.hasUserRatedApp()
    }
}