package com.palmdev.german_books.presentation.screens.videos.video_player

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.palmdev.german_books.data.model.ToDoTask
import com.palmdev.german_books.data.storage.ToDoTasksStorage
import com.palmdev.german_books.domain.model.SavedVideo
import com.palmdev.german_books.domain.model.Subtitle
import com.palmdev.german_books.domain.model.VideoInfo
import com.palmdev.german_books.domain.repository.SavedVideoRepository
import com.palmdev.german_books.domain.repository.UserRepository
import com.palmdev.german_books.domain.repository.VideoRepository
import com.palmdev.german_books.domain.usecases.video.GetVideoByIdUseCase
import com.palmdev.german_books.domain.usecases.video.UpdateVideoIsFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoPlayerViewModel @Inject constructor(
    private val videoRepository: VideoRepository,
    private val userRepository: UserRepository,
    private val getVideoByIdUseCase: GetVideoByIdUseCase,
    private val savedVideoRepository: SavedVideoRepository,
    private val updateVideoIsFavoriteUseCase: UpdateVideoIsFavoriteUseCase,
    private val toDoTasksStorage: ToDoTasksStorage
) : ViewModel() {

    val originalSubtitles = MutableLiveData<List<Subtitle>>()
    val translatedSubtitles = MutableLiveData<List<Subtitle>>()
    val recommendedVideos = MutableLiveData<List<VideoInfo>>()
    var playerCurrentSeconds = 0.0f
    var isPlaying = true
    var withDoubleSubtitles = MutableLiveData(true)
    val isFavorite = MutableLiveData(false)
    val video = MutableLiveData<SavedVideo?>()
    val userLanguage by lazy { userRepository.userLanguage }
    val watchedVideosToday = MutableLiveData(0)
    val isPremiumUser by lazy { userRepository.hasPremium }

    init {
        setTaskCompleted()
    }

    private fun setTaskCompleted() {
        val readingTask = toDoTasksStorage.getTaskByType(taskType = ToDoTask.TaskType.WATCH_VIDEO)
        toDoTasksStorage.saveTask(
            readingTask.copy(
                progress = readingTask.progress + 1
            )
        )
    }

    fun initVideo(videoId: String) {
        video.value?.let {
            if (it.videoId == videoId) return
        }
        viewModelScope.launch(Dispatchers.IO) {
            getVideoByIdUseCase.invoke(videoId)?.let {
                video.postValue(it)
                originalSubtitles.postValue(it.originalSubtitles)
                translatedSubtitles.postValue(it.translatedSubtitles)
                playerCurrentSeconds = it.secondsWatched.toFloat()
                isFavorite.postValue(it.isFavorite)
            }
        }
        watchedVideosToday.postValue(userRepository.getCountWatchedVideosToday())
    }

    fun setRecommendedVideos(videoId: String) {
        video.value?.let {
            if (it.videoId == videoId && recommendedVideos.value != null) return
        }
        viewModelScope.launch(Dispatchers.IO) {
            videoRepository.getRecommendedVideos(videoId).collect {
                recommendedVideos.postValue(it)
            }
        }
    }

    fun saveVideo(video: SavedVideo) {
        viewModelScope.launch(Dispatchers.IO) {
            savedVideoRepository.saveVideo(
                SavedVideo(
                    videoId = video.videoId,
                    category = video.category,
                    title = video.title,
                    imageUrl = video.imageUrl,
                    duration = video.duration,
                    viewsCount = video.viewsCount,
                    likesCount = video.likesCount,
                    secondsWatched = playerCurrentSeconds.toDouble(),
                    originalSubtitles = video.originalSubtitles,
                    translatedSubtitles = video.translatedSubtitles,
                    isFavorite = isFavorite.value ?: false
                )
            )
        }
    }

    fun updateVideoIsFavorite(video: SavedVideo) {
        viewModelScope.launch(Dispatchers.IO) {
            updateVideoIsFavoriteUseCase(
                SavedVideo(
                    id = video.id,
                    videoId = video.videoId,
                    category = video.category,
                    title = video.title,
                    imageUrl = video.imageUrl,
                    duration = video.duration,
                    viewsCount = video.viewsCount,
                    likesCount = video.likesCount,
                    secondsWatched = playerCurrentSeconds.toDouble(),
                    originalSubtitles = video.originalSubtitles,
                    translatedSubtitles = video.translatedSubtitles,
                    isFavorite = isFavorite.value ?: false
                )
            )
        }
    }

    fun addNewWatchedVideo() {
        userRepository.addNewWatchedVideoToday()
        watchedVideosToday.postValue(userRepository.getCountWatchedVideosToday())
    }
}