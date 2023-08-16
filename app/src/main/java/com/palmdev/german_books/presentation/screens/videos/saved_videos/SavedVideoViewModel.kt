package com.palmdev.german_books.presentation.screens.videos.saved_videos

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
class SavedVideoViewModel @Inject constructor(
    private val savedVideoRepository: SavedVideoRepository
) : ViewModel() {

    val savedVideos = MutableLiveData<List<SavedVideo>>()

    init {
        setVideos()
    }
    private fun setVideos() {
        viewModelScope.launch (Dispatchers.IO) {
            savedVideoRepository.getFavorites().collect {
                savedVideos.postValue(it.reversed())
            }
        }
    }
}