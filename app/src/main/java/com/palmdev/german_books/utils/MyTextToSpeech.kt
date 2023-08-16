package com.palmdev.german_books.utils

import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.Locale

class MyTextToSpeech {
    companion object {
        var mTextToSpeech: TextToSpeech? = null

        fun play(text: CharSequence, context: Context) {
            mTextToSpeech = TextToSpeech(context) {
                mTextToSpeech?.language = Locale(Languages.learningLanguage.code)
                mTextToSpeech!!.setSpeechRate(0.6f)
                mTextToSpeech!!.speak(text,TextToSpeech.QUEUE_FLUSH, null, null)
            }

        }
    }
}