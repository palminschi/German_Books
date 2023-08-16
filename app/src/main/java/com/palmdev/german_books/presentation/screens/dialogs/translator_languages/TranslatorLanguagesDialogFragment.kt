package com.palmdev.german_books.presentation.screens.dialogs.translator_languages

//import com.palmdev.german_books.legacy.data.SharedPref
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.mlkit.nl.translate.TranslateLanguage
import com.palmdev.german_books.R
import com.palmdev.german_books.databinding.DialogTranslatorLanguagesBinding
import com.palmdev.german_books.utils.Translate
import com.palmdev.german_books.utils.TranslatorLanguages
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TranslatorLanguagesDialogFragment : DialogFragment(R.layout.dialog_translator_languages) {

    private lateinit var binding: DialogTranslatorLanguagesBinding
    private val viewModel by viewModels<TranslatorLanguagesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogTranslatorLanguagesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // init available languages
        TranslatorLanguages.initLanguages()
        val translatorLanguagesName = TranslatorLanguages.allLanguagesName
        val translatorLanguagesCode = TranslatorLanguages.allLanguagesCode

        isCancelable = false
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // create spinner
        val adapter = ArrayAdapter(
            requireContext(), android.R.layout.simple_spinner_item,
            translatorLanguagesName
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerLanguages.adapter = adapter

        binding.spinnerLanguages.setSelection(
            translatorLanguagesCode.indexOf(viewModel.userLanguage.code)
        )


        // init buttons

        binding.btnDownload.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            binding.btnDownload.visibility = View.INVISIBLE
            val translateToLang =
                translatorLanguagesCode[binding.spinnerLanguages.selectedItemId.toInt()]
            Translate.createTranslator(TranslateLanguage.ENGLISH, translateToLang)
            Translate.getTranslator()
                ?.downloadModelIfNeeded()
                ?.addOnSuccessListener {
                    binding.progressBar.visibility = View.GONE
                    binding.btnDownload.visibility = View.VISIBLE
                    // Save User Languages for Translator
                    viewModel.setUserLanguage(
                        languageCode = translatorLanguagesCode[binding.spinnerLanguages.selectedItemId.toInt()],
                        languageName = translatorLanguagesName[binding.spinnerLanguages.selectedItemId.toInt()]
                    )
                }
                ?.addOnFailureListener {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                }
                ?.addOnCanceledListener {
                    Toast.makeText(context, "Canceled", Toast.LENGTH_SHORT).show()
                }
                ?.addOnCompleteListener { findNavController().popBackStack() }

            val connectivityManager =
                requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                binding.tvNoInternet.visibility = View.GONE
                binding.btnDownload.visibility = View.INVISIBLE
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.tvNoInternet.visibility = View.VISIBLE
                binding.btnDownload.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
            }
        }
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}