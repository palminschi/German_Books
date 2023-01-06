package com.palmdev.german_books.utils

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.Voice
import java.util.*

class VoiceText(private val context: Context) {

    private lateinit var mTextToSpeech: TextToSpeech

    fun init(){
        mTextToSpeech = TextToSpeech(context){
            mTextToSpeech.language = Locale.GERMAN
            mTextToSpeech.setSpeechRate(0.90f)
        }
    }

    fun play(text: String){
        mTextToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }
}