package com.palmdev.german_books.presentation.screens.reading

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.palmdev.german_books.data.model.ToDoTask
import com.palmdev.german_books.data.storage.ToDoTasksStorage
import com.palmdev.german_books.domain.model.ReadBook
import com.palmdev.german_books.domain.model.Word
import com.palmdev.german_books.domain.repository.BooksRepository
import com.palmdev.german_books.domain.repository.SavedWordsRepository
import com.palmdev.german_books.domain.repository.UserRepository
import com.palmdev.german_books.domain.usecases.words.SaveNewWordsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReadingViewModel @Inject constructor(
    private val booksRepository: BooksRepository,
    private val userRepository: UserRepository,
    private val savedWordsRepository: SavedWordsRepository,
    private val saveNewWordsUseCase: SaveNewWordsUseCase,
    private val toDoTasksStorage: ToDoTasksStorage
) : ViewModel() {

    val lastReadBook = MutableLiveData<ReadBook?>()
    val isDarkMode = MutableLiveData(true)
    val fontSize = MutableLiveData(16f)
    var userLanguage = userRepository.userLanguage
    val savedWordsCount = MutableLiveData(0)
    val currentBook = MutableLiveData<ReadBook>()

    init {
        isDarkMode.value = userRepository.isDarkMode
        fontSize.value = userRepository.readingFontSize
        updateSavedWordsCount()
        setTaskCompleted()
    }

    fun updateLastBookRead() {
        viewModelScope.launch(Dispatchers.IO) {
            lastReadBook.postValue(booksRepository.getLastReadBook())
        }
    }

    private fun setTaskCompleted() {
        val readingTask = toDoTasksStorage.getTaskByType(taskType = ToDoTask.TaskType.READ_BOOK)
        toDoTasksStorage.saveTask(
            readingTask.copy(
                progress = readingTask.progress + 1
            )
        )
    }

    fun setBook(bookId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val readBook = booksRepository.getReadBookById(bookId)
            if (readBook == null) {
                booksRepository.getBookById(bookId)?.let {
                    currentBook.postValue(
                        ReadBook(
                            id = it.id,
                            title = it.title,
                            author = it.author,
                            level = it.level,
                            categoryId = it.categoryId,
                            isFavorite = false,
                            isPremium = it.isPremium,
                            imagePath = it.imagePath,
                            contentPath = it.contentPath,
                            currentPage = 0,
                            totalPages = 100
                        )
                    )
                }
            } else {
                readBook.let {
                    currentBook.postValue(it)
                }
            }
        }
    }

    fun saveBook(readBook: ReadBook) {
        viewModelScope.launch(Dispatchers.IO) {
            booksRepository.saveBook(readBook)
        }
    }


    fun updateSavedWordsCount() {
        viewModelScope.launch(Dispatchers.IO) {
            savedWordsCount.postValue(
                savedWordsRepository.getWordsCount()
            )
        }
    }

    fun setCurrentBookPages(currentPage: Int, totalPages: Int) {
        currentBook.value?.let {
            val newObj = it.copy(
                currentPage = currentPage,
                totalPages = totalPages
            )
            viewModelScope.launch(Dispatchers.IO) {
                booksRepository.saveBook(newObj)
            }
        }
    }

    fun saveLastReadBook(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            booksRepository.setLastReadBookId(id)
        }
    }

    fun switchDarkMode() {
        userRepository.switchDarkMode()
        isDarkMode.value = userRepository.isDarkMode
    }

    fun setFontSize(sp: Float) {
        userRepository.setFontSize(sp)
        fontSize.value = userRepository.readingFontSize
    }

    fun updateUserLanguage() {
        userLanguage = userRepository.userLanguage
    }

    fun setIsFavoriteBook(boolean: Boolean, book: ReadBook) {
        viewModelScope.launch(Dispatchers.IO) {
            booksRepository.setBookIsFavorite(boolean, book)
            currentBook.postValue(
                booksRepository.getReadBookById(book.id)
            )
        }
    }

    fun saveWord(word: String, translation: String) {
        viewModelScope.launch(Dispatchers.IO) {
            saveNewWordsUseCase(
                listOf(
                    Word(
                        wordId = word,
                        value = word,
                        translation = translation
                    )
                )
            )
        }
    }
}