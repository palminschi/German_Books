package com.palmdev.german_books.presentation.screens.reading.books

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.palmdev.german_books.domain.model.Book
import com.palmdev.german_books.domain.model.ReadBook
import com.palmdev.german_books.domain.repository.BooksRepository
import com.palmdev.german_books.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BooksViewModel @Inject constructor(
    private val booksRepository: BooksRepository,
    private val userRepository: UserRepository
) :
    ViewModel() {

    val lastReadBook = MutableLiveData<ReadBook?>()
    val a1Books = MutableLiveData<List<Book>>()
    val a2Books = MutableLiveData<List<Book>>()
    val b1Books = MutableLiveData<List<Book>>()
    val b2Books = MutableLiveData<List<Book>>()
    val favorites = MutableLiveData<List<Book>>()
    val isPremiumUser = userRepository.hasPremium

    init {
        initBooks()
        updateLastBookRead()
    }

    fun initBooks() {
        viewModelScope.launch(Dispatchers.IO) {
            a1Books.postValue(booksRepository.getBooksByLevel("A1").shuffled())
            a2Books.postValue(booksRepository.getBooksByLevel("A2").shuffled())
            b1Books.postValue(booksRepository.getBooksByLevel("B1").shuffled())
            b2Books.postValue(booksRepository.getBooksByLevel("B2").shuffled())
        }
    }

    fun updateLastBookRead() {
        viewModelScope.launch(Dispatchers.IO) {
            lastReadBook.postValue(booksRepository.getLastReadBook())
        }
    }

    fun initFavoriteBooks() {
        viewModelScope.launch(Dispatchers.IO) {
            favorites.postValue(booksRepository.getFavoriteBooks().map {
                Book(
                    id = it.id,
                    title = it.title,
                    author = it.author,
                    level = it.level,
                    categoryId = it.categoryId,
                    isPremium = it.isPremium,
                    imagePath = it.imagePath,
                    contentPath = it.contentPath
                )
            })
        }
    }
}