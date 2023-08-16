package com.palmdev.german_books.domain.repository

import com.palmdev.german_books.domain.model.Language

interface UserRepository {

    val userLanguage: Language

    fun setUserLanguage(language: Language)

    val hasPremium: Boolean

    fun setPremiumStatus(boolean: Boolean)

    val hasRatedApp: Boolean

    fun setUserRatedApp(boolean: Boolean)

    val isFirstSessionToday: Boolean

    val isDarkMode: Boolean

    fun switchDarkMode()

    val readingFontSize: Float

    fun setFontSize(sp: Float)

    fun getCountWatchedVideosToday(): Int

    fun addNewWatchedVideoToday()

    val dailyStreak: Int
}