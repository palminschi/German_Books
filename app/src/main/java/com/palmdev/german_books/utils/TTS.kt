package com.palmdev.german_books.utils

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.speech.tts.TextToSpeech
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Runnable
import java.util.Locale


class TTS(context: Context) : TextToSpeech.OnInitListener {

    private val tts = TextToSpeech(context, this)
    private val isReady = MutableLiveData(false)
    val speechStarted = MutableLiveData(false)

    fun isSpeaking(): Boolean = tts.isSpeaking


    override fun onInit(initStatus: Int) {
        if (initStatus == TextToSpeech.SUCCESS) {

            tts.language = Locale(Languages.learningLanguage.code)
            tts.setSpeechRate(0.60f)
            isReady.value = true
        }
    }

    fun say(text: String) {
        if (isReady.value!!) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
            speechStarted.value = true
        } else {
            var tryCounter = 0
            val handler = Handler(Looper.getMainLooper())
            handler.post(object : Runnable {
                override fun run() {
                    if (tryCounter > 10) return
                    if (isReady.value!!) {
                        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
                        speechStarted.value = true
                        return
                    }
                    tryCounter++
                    handler.postDelayed(this, 500)
                }
            })
        }

    }


}