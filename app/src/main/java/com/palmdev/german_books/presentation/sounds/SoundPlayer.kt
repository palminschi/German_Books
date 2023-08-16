package com.palmdev.german_books.presentation.sounds

import android.content.Context
import android.media.MediaPlayer
import com.palmdev.german_books.R

enum class Sound { CLICK, SUCCESS, AUDIO_RECORD, FAILED, CONGRATS }

class SoundPlayer(context: Context, sound: Sound) {

    private var mMediaPlayer: MediaPlayer? = null

    init {
        val resource = when (sound) {
            Sound.CLICK -> R.raw.sound_click
            Sound.AUDIO_RECORD -> R.raw.sound_start_record
            Sound.SUCCESS -> R.raw.sound_success
            Sound.FAILED -> R.raw.sound_error
            Sound.CONGRATS -> R.raw.sound_congrats
        }
        mMediaPlayer = MediaPlayer.create(context, resource)
        if (sound == Sound.CLICK) mMediaPlayer?.setVolume(0.4f, 0.4f)
    }

    fun play() {
        mMediaPlayer?.start()
        mMediaPlayer?.setOnCompletionListener {
            it.release()
            mMediaPlayer = null
        }
    }
}