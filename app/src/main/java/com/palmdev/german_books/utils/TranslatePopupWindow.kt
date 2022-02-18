package com.palmdev.german_books.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.TextView
import com.palmdev.german_books.R

object TranslatePopupWindow {

    fun getPopupWindow(context: Context, word: String, translatedWord: String?): PopupWindow{
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.popup_translate_word, null)

        val popupWindow = PopupWindow(
            view,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            true
        )

        val popupText = popupWindow.contentView.findViewById<TextView>(R.id.popupText)
        val popupTranslatedText = popupWindow.contentView.findViewById<TextView>(R.id.popupTranslatedText)
        popupText.text = word
        popupTranslatedText.text = translatedWord

        return popupWindow
    }
}