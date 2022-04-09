package com.palmdev.german_books.presentation.screens.dialog_save_word

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import com.palmdev.german_books.R
import com.palmdev.german_books.databinding.DialogSaveWordBinding
import com.palmdev.german_books.utils.GoogleMLKitTranslator
import com.palmdev.german_books.utils.VoiceText
import org.koin.androidx.viewmodel.ext.android.viewModel

class SaveWordDialogFragment(
    private val word: String? = null,
    private val translatedWord: String? = null
) : DialogFragment() {

    private lateinit var binding: DialogSaveWordBinding
    private val viewModel : SaveWordViewModel by viewModel()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val view = layoutInflater.inflate(R.layout.dialog_save_word, null)

        binding = DialogSaveWordBinding.bind(view)

        // Create dialog
        val dialog = Dialog(view.context)
        dialog.setContentView(view)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // Set texts
        binding.dialogWord.setText(word)
        binding.dialogTranslatedWord.setText(translatedWord)

        // Text to speech
        val voiceText = VoiceText(requireContext())
        voiceText.init()
        binding.btnSound.setOnClickListener {
            voiceText.play(binding.dialogWord.text.toString())
        }

        // Buttons
        binding.btnCancel.setOnClickListener { dialog.dismiss() }
        binding.btnSave.setOnClickListener {
            viewModel.addWord(
                word = binding.dialogWord.text.toString(),
                translation = binding.dialogTranslatedWord.text.toString()
            )
            dialog.dismiss()
        }
        binding.btnTranslate.setOnClickListener { translate() }

        translate()

        return dialog
    }

    private fun translate(){
        val word = binding.dialogWord.text.toString()
        if (word.isNotEmpty()) {
            binding.dialogTranslatedWord.visibility = View.INVISIBLE
            binding.progressBar.visibility = View.VISIBLE
            GoogleMLKitTranslator
                .translate(word)
                ?.addOnSuccessListener {
                    binding.dialogTranslatedWord.setText(it)
                    binding.dialogTranslatedWord.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.INVISIBLE
                }
        } else {
            binding.progressBar.visibility = View.INVISIBLE
            binding.dialogTranslatedWord.visibility = View.VISIBLE
        }
    }

}