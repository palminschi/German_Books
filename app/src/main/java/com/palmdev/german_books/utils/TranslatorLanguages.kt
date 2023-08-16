package com.palmdev.german_books.utils

import com.google.mlkit.nl.translate.TranslateLanguage
import com.palmdev.german_books.legacy.models.TranslatorLanguage
import java.util.Locale

class TranslatorLanguages {
    companion object {
        var allTranslatorLanguages = ArrayList<TranslatorLanguage>()
        var allLanguagesName = ArrayList<String>()
        var allLanguagesCode = ArrayList<String>()

        fun initLanguages() {
            // get all available languages for translate
            val availableLanguagesCode = TranslateLanguage.getAllLanguages()
            // put all languages(with name and code) to arraylist
            for (i in 0 until availableLanguagesCode.size) {
                val locale = Locale(availableLanguagesCode[i])
                var langName = locale.getDisplayName(locale)
                langName = langName.substring(0,1).uppercase(Locale.getDefault()) + langName.substring(1)
                val lang = TranslatorLanguage(langName,availableLanguagesCode[i])
                allTranslatorLanguages.add(lang)
                allLanguagesName.add(langName)
                allLanguagesCode.add(availableLanguagesCode[i])
            }
        }
    }
}
