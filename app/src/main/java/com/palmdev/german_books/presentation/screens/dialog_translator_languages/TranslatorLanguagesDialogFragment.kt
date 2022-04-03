package com.palmdev.german_books.presentation.screens.dialog_translator_languages

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import com.palmdev.german_books.R
import com.palmdev.german_books.databinding.DialogTranslatorLanguagesBinding
import com.palmdev.german_books.utils.GoogleMLKitTranslator
import org.koin.androidx.viewmodel.ext.android.viewModel

class TranslatorLanguagesDialogFragment : DialogFragment() {

    private val viewModel : TranslatorLanguagesViewModel by viewModel()
    private lateinit var binding: DialogTranslatorLanguagesBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dialog = Dialog(requireContext())
        val view = layoutInflater.inflate(R.layout.dialog_translator_languages, null)
        binding = DialogTranslatorLanguagesBinding.bind(view)
        dialog.setContentView(view)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // Spinner with available languages
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            viewModel.availableLanguageNames
        )
        binding.spinnerLanguages.adapter = adapter

        // Download translator if needed
        binding.btnDownload.setOnClickListener {
            it.visibility = View.INVISIBLE
            binding.progressBar.visibility = View.VISIBLE

            val selectedItemId = binding.spinnerLanguages.selectedItemId.toInt()
            val translateTo = viewModel.availableLanguageCodes[selectedItemId]
            GoogleMLKitTranslator.createTranslator(
                fromLanguage = "de",
                toLanguage = translateTo
            )?.addOnSuccessListener {
                binding.btnDownload.visibility = View.VISIBLE
                binding.progressBar.visibility = View.INVISIBLE

                viewModel.saveTranslatorPreferences(binding.spinnerLanguages.selectedItem.toString())
                dialog.dismiss()
            }
        }
        return dialog
    }

}