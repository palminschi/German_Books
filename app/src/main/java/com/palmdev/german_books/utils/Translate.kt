package com.palmdev.german_books.utils

import com.google.android.gms.tasks.Task
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions

object Translate {
    private var translator: Translator? = null

    fun getTranslator(): Translator? {
        return translator
    }

    fun createTranslator(sourceLanguage: String, targetLanguage: String): Task<Void> {
        val options = TranslatorOptions.Builder()
            .setSourceLanguage(sourceLanguage)
            .setTargetLanguage(targetLanguage)
            .build()
        val tempTranslator = Translation.getClient(options)

        return tempTranslator.downloadModelIfNeeded()
            .addOnSuccessListener {
                translator = tempTranslator

            }.addOnFailureListener {
                translator = null
            }
    }

    fun downloadModel() {
        val condition = DownloadConditions.Builder().requireWifi().build()
        translator?.downloadModelIfNeeded(condition)
    }
}