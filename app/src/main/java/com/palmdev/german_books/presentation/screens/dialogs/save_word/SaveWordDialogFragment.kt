package com.palmdev.german_books.presentation.screens.dialogs.save_word

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.mlkit.nl.translate.Translator
import com.palmdev.german_books.R
import com.palmdev.german_books.databinding.DialogAddWordBinding
import com.palmdev.german_books.utils.Languages
import com.palmdev.german_books.utils.TTS
import com.palmdev.german_books.utils.Translate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SaveWordDialogFragment : DialogFragment() {

    private lateinit var binding: DialogAddWordBinding
    private val viewModel by viewModels<SaveWordViewModel>()
    private var mTranslator: Translator? = null
    private val mTTS by lazy { TTS(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogAddWordBinding.inflate(layoutInflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Translate.createTranslator(
            sourceLanguage = Languages.learningLanguage.code,
            targetLanguage = viewModel.userLanguage.code
        ).addOnSuccessListener {
            mTranslator = Translate.getTranslator()

            val textToTranslate = arguments?.getString(ARG_TEXT_TO_TRANSLATE)

            textToTranslate?.let { text ->
                binding.dialogWord.text = text
                mTranslator?.translate(text)?.addOnSuccessListener {
                    binding.loading.visibility = View.GONE
                    binding.dialogTranslatedWord.visibility = View.VISIBLE
                    binding.dialogTranslatedWord.text = it
                }
            }
        }

        binding.btnCancel.setOnClickListener { findNavController().popBackStack() }

        binding.btnAdd.setOnClickListener {
            if (binding.dialogTranslatedWord.text.isNotEmpty()) {
                viewModel.saveWord(
                    word = binding.dialogWord.text.toString(),
                    translation = binding.dialogTranslatedWord.text.toString()
                )
                findNavController().popBackStack()
            } else {
                val snackbar = Snackbar.make(
                    binding.root,
                    getText(R.string.selectText),
                    Snackbar.LENGTH_SHORT
                )
                snackbar.setTextColor(resources.getColor(R.color.white, null))
                snackbar.setBackgroundTint(resources.getColor(R.color.night_background, null))
                snackbar.show()
            }
        }

        binding.btnSound.setOnClickListener {
            mTTS.say(binding.dialogWord.text.toString())
        }
    }

    companion object {
        const val ARG_TEXT_TO_TRANSLATE = "ARG_TEXT_TO_TRANSLATE"
    }
}