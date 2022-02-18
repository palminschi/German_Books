package com.palmdev.german_books.presentation.screens.book_reading

import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.palmdev.domain.model.Language
import com.palmdev.domain.usecase.user.GetUserLanguageUseCase
import com.palmdev.domain.usecase.user.SaveTranslatorPreferencesUseCase
import com.palmdev.german_books.utils.GoogleMLKitTranslator

class TranslatorLanguagesViewModel(
    private val saveTranslatorPreferencesUseCase: SaveTranslatorPreferencesUseCase,
    private val getUserLanguageUseCase: GetUserLanguageUseCase
) : ViewModel() {

    private val _userLanguage = MutableLiveData<Language>()
    private val _availableLanguages = MutableLiveData<List<Language>>()
    val userLanguage: LiveData<Language> = _userLanguage
    val availableLanguages: LiveData<List<Language>> = _availableLanguages
    var availableLanguageNames = ArrayList<String>()
    var availableLanguageCodes = ArrayList<String>()

    init {
        _userLanguage.value = getUserLanguageUseCase.execute()
        _availableLanguages.value = GoogleMLKitTranslator.getAllAvailableLanguages()

        _availableLanguages.value!!.map {
            availableLanguageNames.add(it.name)
            availableLanguageCodes.add(it.code)
        }
    }

    fun saveTranslatorPreferences(languageName: String){
        val index = availableLanguageNames.indexOf(languageName)

        val prefLang = Language(
            name = languageName,
            code = availableLanguageCodes[index]
        )

        saveTranslatorPreferencesUseCase.execute(prefLang)
    }


}