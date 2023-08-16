package com.palmdev.german_books.presentation.screens.videos

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.palmdev.german_books.domain.model.SavedVideo
import com.palmdev.german_books.domain.model.VideoInfo
import com.palmdev.german_books.domain.repository.SavedVideoRepository
import com.palmdev.german_books.domain.repository.SavedWordsRepository
import com.palmdev.german_books.domain.repository.UserRepository
import com.palmdev.german_books.domain.repository.VideoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideosViewModel @Inject constructor(
    private val videoRepository: VideoRepository,
    private val savedVideoRepository: SavedVideoRepository,
    private val savedWordsRepository: SavedWordsRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    val popularVideos = MutableLiveData<List<VideoInfo>>()
    val topRatedVideos = MutableLiveData<List<VideoInfo>>()
    val recommendedVideos = MutableLiveData<List<VideoInfo>?>()
    val videos = MutableLiveData<List<VideoInfo>?>()
    val lastWatchedVideo = MutableLiveData<SavedVideo>()
    val wordsToRepeatCount = MutableLiveData(0)
    val watchedVideosToday = MutableLiveData(0)
    val isPremiumUser = MutableLiveData(userRepository.hasPremium)

    init {
        initPopularVideos()
        initTopRatedVideos()
        getRecommendedVideos()
        initLastWatchedVideo()
        viewModelScope.launch(Dispatchers.IO) {
            wordsToRepeatCount.postValue(
                savedWordsRepository.getWordsToRepeatCount(System.currentTimeMillis())
            )
        }
        watchedVideosToday.postValue(userRepository.getCountWatchedVideosToday())
    }

    private fun initLastWatchedVideo() {
        viewModelScope.launch(Dispatchers.IO) {
            savedVideoRepository.getLastVideo()?.let {
                lastWatchedVideo.postValue(it)
            }
        }
    }

    private fun initPopularVideos() {
        viewModelScope.launch(Dispatchers.IO) {
            videoRepository.getPopularVideos().collect {
                popularVideos.postValue(it.shuffled())
            }
        }
    }

    private fun initTopRatedVideos() {
        viewModelScope.launch(Dispatchers.IO) {
            videoRepository.getTopRatedVideos().collect {
                topRatedVideos.postValue(it.shuffled())
            }
        }
    }

     private fun getRecommendedVideos() {
        viewModelScope.launch(Dispatchers.IO) {
            videoRepository.getRecommendedVideos(null).collect {
                recommendedVideos.postValue(it.shuffled())
            }
        }
    }

    fun getAllVideos() {
        viewModelScope.launch(Dispatchers.IO) {
            videos.postValue(videoRepository.getAllVideos().shuffled())
        }
    }

    fun addNewWatchedVideo() {
        userRepository.addNewWatchedVideoToday()
        watchedVideosToday.postValue(userRepository.getCountWatchedVideosToday())
    }

    fun updatePremiumStatus() {
        isPremiumUser.postValue(userRepository.hasPremium)
    }

}
