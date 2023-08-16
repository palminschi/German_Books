package com.palmdev.german_books.data.storage

import android.content.Context
import org.threeten.bp.LocalDate

const val WATCHED_VIDEOS_TODAY = "WATCHED_VIDEOS_TODAY"
const val LAST_WATCHED_DATE = "LAST_WATCHED_DATE"

class VideoWatchedManager(context: Context) {

    private val prefs = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)

    fun addWatchedVideo() {
        val watchedVideosToday = prefs.getInt(WATCHED_VIDEOS_TODAY, 0)

        prefs.edit()
            .putInt(WATCHED_VIDEOS_TODAY, watchedVideosToday + 1)
            .apply()
    }

    fun getWatchedVideoCount(): Int {
        val watchedVideosToday = prefs.getInt(WATCHED_VIDEOS_TODAY, 0)
        val today = LocalDate.now()

        // If the last watched date is not today, reset the count to 0
        val lastWatchedDateString = prefs.getString(LAST_WATCHED_DATE, null)
        val lastWatchedDate = lastWatchedDateString?.let { LocalDate.parse(it) }
        if (lastWatchedDate == null || lastWatchedDate != today) {
            prefs.edit()
                .putString(LAST_WATCHED_DATE, today.toString())
                .putInt(WATCHED_VIDEOS_TODAY, 0)
                .apply()
            return 0
        }

        return watchedVideosToday
    }
}