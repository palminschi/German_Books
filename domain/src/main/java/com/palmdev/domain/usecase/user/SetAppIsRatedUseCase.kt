package com.palmdev.domain.usecase.user

import com.palmdev.domain.repository.UserRepository

class SetAppIsRatedUseCase(private val userRepository: UserRepository) {

    fun execute(boolean: Boolean) {
        userRepository.setAppIsRated(boolean)
    }

}