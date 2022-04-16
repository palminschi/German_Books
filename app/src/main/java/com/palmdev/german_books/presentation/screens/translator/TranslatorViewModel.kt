package com.palmdev.german_books.presentation.screens.translator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.palmdev.domain.model.Language
import com.palmdev.domain.usecase.user.GetTranslatorPreferencesUseCase

class TranslatorViewModel(
    private val getTranslatorPreferencesUseCase: GetTranslatorPreferencesUseCase,
) : ViewModel() {

    private val _translatorPrefs = MutableLiveData<Language>()
    val translatorPrefs: LiveData<Language> = _translatorPrefs

    fun initTranslatorPrefs(){
        _translatorPrefs.value = getTranslatorPreferencesUseCase.execute()
    }
}