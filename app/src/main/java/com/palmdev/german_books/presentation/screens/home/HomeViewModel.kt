package com.palmdev.german_books.presentation.screens.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.palmdev.german_books.data.model.ToDoTask
import com.palmdev.german_books.data.storage.ToDoTasksStorage
import com.palmdev.german_books.domain.model.Book
import com.palmdev.german_books.domain.model.ReadBook
import com.palmdev.german_books.domain.model.VideoInfo
import com.palmdev.german_books.domain.model.Word
import com.palmdev.german_books.domain.repository.BooksRepository
import com.palmdev.german_books.domain.repository.SavedWordsRepository
import com.palmdev.german_books.domain.repository.UserRepository
import com.palmdev.german_books.domain.repository.VideoRepository
import com.palmdev.german_books.domain.usecases.CheckSubscriptionUseCase
import com.palmdev.german_books.domain.usecases.words.SaveNewWordsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val savedWordsRepository: SavedWordsRepository,
    private val saveNewWordsUseCase: SaveNewWordsUseCase,
    private val toDoTasksStorage: ToDoTasksStorage,
    private val booksRepository: BooksRepository,
    private val videoRepository: VideoRepository,
    private val checkSubscriptionUseCase: CheckSubscriptionUseCase
) : ViewModel() {

    var userRatedApp: Boolean = userRepository.hasRatedApp
    val savedWordsCount = MutableLiveData<Int>()
    val toDoTasks = MutableLiveData<List<ToDoTask>>()
    val lastReadBook = MutableLiveData<ReadBook>(null)
    val newBooks = MutableLiveData<List<Book>>()
    val videos = MutableLiveData<List<VideoInfo>>()
    val watchedVideosToday = MutableLiveData(0)
    val dailyStreak = userRepository.dailyStreak

    init {
        initSavedWordsCount()
        setNewBooks()
        initVideos()
    }

    fun isFirstSessionToday(): Boolean = userRepository.isFirstSessionToday

    fun hasPremium(): Boolean = userRepository.hasPremium

    fun setUserRatedApp() {
        userRepository.setUserRatedApp(true)
        userRatedApp = true
    }

    fun saveWords(words: List<Word>) {
        viewModelScope.launch(Dispatchers.IO) {
            saveNewWordsUseCase.invoke(words)
            initSavedWordsCount()
        }
    }

    private fun initSavedWordsCount() {
        viewModelScope.launch(Dispatchers.IO) {
            savedWordsCount.postValue(savedWordsRepository.getWordsCount())
        }
    }

    fun updateTasks() {
        toDoTasks.postValue(
            toDoTasksStorage.getAllTasks()
        )
    }

    fun updateLastReadBook() {
        viewModelScope.launch (Dispatchers.IO){
            lastReadBook.postValue(booksRepository.getLastReadBook())
        }
    }

    private fun setNewBooks() {
        viewModelScope.launch (Dispatchers.IO) {
            val list = listOf(
                booksRepository.getBookById(1),
                booksRepository.getBookById(2),
                booksRepository.getBookById(3),
                booksRepository.getBookById(4),
                booksRepository.getBookById(5)
            )
            newBooks.postValue(list.requireNoNulls())
        }
    }

    private fun initVideos() {
        viewModelScope.launch(Dispatchers.IO) {
            videoRepository.getPopularVideos().collect {
                videos.postValue(it.shuffled())
            }
        }
    }

    fun addNewWatchedVideo() {
        userRepository.addNewWatchedVideoToday()
        watchedVideosToday.postValue(userRepository.getCountWatchedVideosToday())
    }

    fun checkSubscription() {
        checkSubscriptionUseCase()
    }
}