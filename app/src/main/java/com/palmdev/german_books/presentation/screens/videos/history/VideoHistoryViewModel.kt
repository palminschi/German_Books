package com.palmdev.german_books.presentation.screens.videos.history

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.palmdev.german_books.domain.model.SavedVideo
import com.palmdev.german_books.domain.repository.SavedVideoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoHistoryViewModel @Inject constructor(
    private val savedVideoRepository: SavedVideoRepository
) : ViewModel() {

    val watchedVideos = MutableLiveData<List<SavedVideo>>()

    init {
        setVideos()
    }
    private fun setVideos() {
        viewModelScope.launch (Dispatchers.IO) {
            savedVideoRepository.getAll().collect {
                watchedVideos.postValue(it.reversed())
            }
        }
    }
}