package com.palmdev.german_books.utils

import android.text.Spannable
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.Gravity
import android.view.View
import android.widget.PopupWindow
import android.widget.TextView
import java.text.BreakIterator
import java.util.*

class TextToClickable {

    private var mClickedWord = ""
    private var xCoordinate = 0
    private var yCoordinate = 0
    fun setCoordinates(x: Int, y: Int){
        xCoordinate = x
        yCoordinate = y
    }


    fun convertTextToClickable(content: CharSequence, textView: TextView) {
        //First - trim the text and remove the spaces at start and end.
        val textContent = content.toString().trim { it <= ' ' }

        //Set the text as SPANNABLE text.
        textView.movementMethod = LinkMovementMethod.getInstance()
        textView.setText(textContent, TextView.BufferType.SPANNABLE)

        //After we get the spans of the text with iterator and we initialized the iterator
        val spans = textView.text as Spannable
        val iterator = BreakIterator.getWordInstance(Locale.US) // <- Input text language
        iterator.setText(textContent)
        var start = iterator.first()

        //Here we get all possible words by iterators
        var end = iterator.next()
        while (end != BreakIterator.DONE) {
            val possibleWord = textContent.substring(start, end)
            if (Character.isLetterOrDigit(possibleWord[0])) {
                val clickSpan = getClickableSpan(possibleWord)
                spans.setSpan(clickSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                //mClickedWord = possibleWord.toString()
            }
            start = end
            end = iterator.next()
        }
    }


    private fun getClickableSpan(word: String): ClickableSpan {
        return object : ClickableSpan() {
            override fun onClick(widget: View) {
                mClickedWord = word
                GoogleMLKitTranslator.translate(word)?.addOnSuccessListener {
                    TranslatePopupWindow.getPopupWindow(widget.context, word, it)
                        .showAtLocation(widget, Gravity.NO_GRAVITY, xCoordinate, yCoordinate)
                }

            }
        }
    }
}