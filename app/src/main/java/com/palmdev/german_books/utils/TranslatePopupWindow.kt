package com.palmdev.german_books.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.fragment.app.FragmentManager
import com.palmdev.german_books.R
import com.palmdev.german_books.databinding.PopupTranslateWordBinding
import com.palmdev.german_books.presentation.screens.dialog_save_word.SaveWordDialogFragment

class TranslatePopupWindow(private val fragmentManager: FragmentManager) {

    private lateinit var binding: PopupTranslateWordBinding

    fun getPopupWindow(context: Context, word: String, translatedWord: String?): PopupWindow{
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.popup_translate_word, null)
        binding = PopupTranslateWordBinding.bind(view)

        // Create PopupWindow
        val popupWindow = PopupWindow(
            view,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            true
        )
        // Set words
        binding.popupText.text = word
        binding.popupTranslatedText.text = translatedWord

        binding.btnClose.setOnClickListener { popupWindow.dismiss() }
        // Open dialog for saving
        binding.btnSave.setOnClickListener {
            popupWindow.dismiss()
            val dialog = SaveWordDialogFragment(word = word, translatedWord = translatedWord)
            dialog.show(fragmentManager, "TAG")
        }
        // Text to speech
        val voiceText = VoiceText(context)
        voiceText.init()
        binding.btnSound.setOnClickListener {
            voiceText.play(word)
        }


        return popupWindow
    }
}