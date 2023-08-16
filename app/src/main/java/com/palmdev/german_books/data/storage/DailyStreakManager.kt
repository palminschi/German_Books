package com.palmdev.german_books.data.storage

import android.content.Context
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.ResolverStyle
import javax.inject.Inject

private const val LAST_USED_DATE_KEY = "LAST_USED_DATE_KEY"
private const val STREAK_COUNT_KEY = "STREAK_COUNT_KEY"

class DailyStreakManager @Inject constructor(context: Context) {

    private val prefs = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
    private val dateFormatter = DateTimeFormatter.ofPattern("uuuu-MM-dd").withResolverStyle(
        ResolverStyle.STRICT)

    fun getUpdatedStreak(): Int {
        val today = LocalDate.now()
        val lastUsedDateString = prefs.getString(LAST_USED_DATE_KEY, null)
        val streakCount = prefs.getInt(STREAK_COUNT_KEY, 0)

        if (lastUsedDateString == null) {
            // First time using the app
            prefs.edit()
                .putString(LAST_USED_DATE_KEY, today.format(dateFormatter))
                .putInt(STREAK_COUNT_KEY, 1)
                .apply()
            return 1
        }

        val lastUsedDate = LocalDate.parse(lastUsedDateString, dateFormatter)
        if (lastUsedDate == today.minusDays(1)) {
            // Increase the streak if the app was used yesterday
            val newStreakCount = streakCount + 1
            prefs.edit()
                .putString(LAST_USED_DATE_KEY, today.format(dateFormatter))
                .putInt(STREAK_COUNT_KEY, newStreakCount)
                .apply()
            return newStreakCount
        } else if (lastUsedDate == today) {
            return streakCount
        } else {
            // Reset the streak if the app was not used yesterday
            prefs.edit()
                .putString(LAST_USED_DATE_KEY, today.format(dateFormatter))
                .putInt(STREAK_COUNT_KEY, 1)
                .apply()
            return 1
        }
    }

}