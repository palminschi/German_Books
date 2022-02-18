package com.palmdev.german_books.presentation.screens.book_reading

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.DialogFragment
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions
import com.palmdev.german_books.R
import com.palmdev.german_books.utils.GoogleMLKitTranslator
import org.koin.androidx.viewmodel.ext.android.viewModel

class TranslatorLanguagesDialogFragment(private val cancelable: Boolean) : DialogFragment() {

    private val viewModel : TranslatorLanguagesViewModel by viewModel()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_translator_languages)

        val spinner = dialog.findViewById<Spinner>(R.id.spinnerLanguages)

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            viewModel.availableLanguageNames
        )
        spinner.adapter = adapter

        dialog.setCancelable(cancelable)

        val btnDownload = dialog.findViewById<Button>(R.id.btnDownload)
        btnDownload.setOnClickListener {
            val selectedItemId = spinner.selectedItemId.toInt()
            val translateTo = viewModel.availableLanguageCodes[selectedItemId]
            GoogleMLKitTranslator.createTranslator(
                fromLanguage = "en",
                toLanguage = translateTo
            )?.addOnSuccessListener {
                viewModel.saveTranslatorPreferences(spinner.selectedItem.toString())
                Log.d("AAA", "Translator downloaded")
                dialog.dismiss()
            }
        }

        return dialog
    }


}