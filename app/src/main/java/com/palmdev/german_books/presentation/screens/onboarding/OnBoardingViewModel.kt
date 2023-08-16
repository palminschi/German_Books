package com.palmdev.german_books.presentation.screens.onboarding

import androidx.lifecycle.ViewModel
import com.palmdev.german_books.domain.model.Language
import com.palmdev.german_books.domain.repository.UserRepository
import com.palmdev.german_books.domain.usecases.CheckSubscriptionUseCase
import com.palmdev.german_books.utils.Languages
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val checkSubscriptionUseCase: CheckSubscriptionUseCase
    ) : ViewModel() {
    val userLanguage = userRepository.userLanguage

    init {
        checkSubscriptionUseCase()
    }

    fun setUserLanguage(languageName: String, languageCode: String) {
        val existLang = Languages.availableUserLanguages.find { it.code == languageCode }
        existLang?.let {
            userRepository.setUserLanguage(it)
            return
        }
        userRepository.setUserLanguage(
            Language(
                code = languageCode,
                name = languageName
            )
        )

    }
}