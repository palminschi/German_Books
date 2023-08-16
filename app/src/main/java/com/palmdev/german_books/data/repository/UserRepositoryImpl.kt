package com.palmdev.german_books.data.repository

import com.palmdev.german_books.data.storage.DailyStreakManager
import com.palmdev.german_books.data.storage.UserStorage
import com.palmdev.german_books.data.storage.VideoWatchedManager
import com.palmdev.german_books.domain.model.Language
import com.palmdev.german_books.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userStorage: UserStorage,
    private val videoWatchedManager: VideoWatchedManager,
    private val dailyStreakManager: DailyStreakManager
    ) : UserRepository {

    override val userLanguage: Language
        get() = userStorage.userLanguage

    override fun setUserLanguage(language: Language) {
        userStorage.setUserLanguage(language)
    }

    override val hasPremium: Boolean
        get() = userStorage.hasPremium

    override fun setPremiumStatus(boolean: Boolean) {
        userStorage.setPremiumStatus(boolean)
    }

    override val hasRatedApp: Boolean
        get() = userStorage.hasRatedApp

    override fun setUserRatedApp(boolean: Boolean) {
        userStorage.setUserRatedApp()
    }

    override val isFirstSessionToday: Boolean
        get() = userStorage.isFirstSessionToday()

    override val isDarkMode: Boolean
        get() = userStorage.isDarkMode

    override fun switchDarkMode() {
        userStorage.switchDarkMode()
    }

    override val readingFontSize: Float
        get() = userStorage.fontSize

    override fun setFontSize(sp: Float) {
        userStorage.setFontSize(sp)
    }

    override fun getCountWatchedVideosToday(): Int {
        return videoWatchedManager.getWatchedVideoCount()
    }

    override fun addNewWatchedVideoToday() {
        videoWatchedManager.addWatchedVideo()
    }

    override val dailyStreak: Int get() = dailyStreakManager.getUpdatedStreak()

}