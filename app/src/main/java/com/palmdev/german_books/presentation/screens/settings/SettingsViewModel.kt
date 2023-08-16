package com.palmdev.german_books.presentation.screens.settings

import androidx.lifecycle.ViewModel
import com.palmdev.german_books.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    val userLanguage = userRepository.userLanguage

}