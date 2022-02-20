package com.palmdev.german_books.utils

import com.google.android.gms.tasks.Task
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions
import com.palmdev.domain.model.Language
import java.util.*

object GoogleMLKitTranslator {

    private var translator: Translator? = null

    fun createTranslator(fromLanguage: String = "de", toLanguage: String): Task<Void>? {
        val options = TranslatorOptions.Builder()
            .setSourceLanguage(fromLanguage)
            .setTargetLanguage(toLanguage)
            .build()
        translator = Translation.getClient(options)
        return translator?.downloadModelIfNeeded()
    }

    fun translate(text: String): Task<String>? {
        return translator?.translate(text)
    }

    fun getAllAvailableLanguages(): List<Language> {

        val listOfLanguages = ArrayList<Language>()

        val availableLanguagesCode = TranslateLanguage.getAllLanguages()
        for (i in 0 until availableLanguagesCode.size) {

            val languageName = Locale(availableLanguagesCode[i]).displayName

            listOfLanguages.add(
                Language(
                    name = languageName,
                    code = availableLanguagesCode[i]
                )
            )
        }
        return listOfLanguages
    }
}