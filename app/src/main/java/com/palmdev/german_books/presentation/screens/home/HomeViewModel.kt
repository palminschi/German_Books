package com.palmdev.german_books.presentation.screens.home

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.palmdev.domain.model.Book
import com.palmdev.domain.model.BookReadingProgress
import com.palmdev.domain.model.GroupOfWords
import com.palmdev.domain.model.Word
import com.palmdev.domain.usecase.books.GetBookByIdUseCase
import com.palmdev.domain.usecase.books.GetLastBookReadUseCase
import com.palmdev.domain.usecase.books.GetReadingProgressUseCase
import com.palmdev.domain.usecase.purchases.GetPremiumStatusUseCase
import com.palmdev.domain.usecase.user.HasUserRatedAppUseCase
import com.palmdev.domain.usecase.user.SetAppIsRatedUseCase
import com.palmdev.domain.usecase.words.AddWordUseCase
import com.palmdev.domain.usecase.words.GetAllWordsUseCase
import com.palmdev.domain.usecase.words.GetGroupsOfWordsUseCase
import com.palmdev.domain.usecase.words.GetWordsByGroupUseCase
import com.palmdev.german_books.utils.AdMob
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getLastBookReadUseCase: GetLastBookReadUseCase,
    private val getAllWordsUseCase: GetAllWordsUseCase,
    private val setAppIsRatedUseCase: SetAppIsRatedUseCase,
    private val hasUserRatedAppUseCase: HasUserRatedAppUseCase,
    private val getReadingProgressUseCase: GetReadingProgressUseCase,
    private val getBookByIdUseCase: GetBookByIdUseCase,
    private val getPremiumStatusUseCase: GetPremiumStatusUseCase
) : ViewModel() {

    private val _userRatedApp = MutableLiveData<Boolean>()
    val userRatedApp: LiveData<Boolean> = _userRatedApp

    private val _lastBook = MutableLiveData<Book>()
    val lastBook: LiveData<Book> = _lastBook

    private val _lastWord = MutableLiveData<Word?>()
    val lastWord: LiveData<Word?> = _lastWord

    private val _lastBookReadingProgress = MutableLiveData<BookReadingProgress>()
    val lastBookReadingProgress: LiveData<BookReadingProgress> = _lastBookReadingProgress

    private val _newBooks = MutableLiveData<List<Book?>>()
    val newBooks: LiveData<List<Book?>> = _newBooks

    private val _userPremiumStatus = MutableLiveData<Boolean>()
    val userPremiumStatus: LiveData<Boolean> = _userPremiumStatus

    init {
        _userRatedApp.value = hasUserRatedAppUseCase.execute()
        _userPremiumStatus.value = getPremiumStatusUseCase.execute()
    }

    fun initNewBooks(id1: Int, id2: Int, id3: Int) {
        _newBooks.value = arrayListOf(
            getBookByIdUseCase.execute(id1),
            getBookByIdUseCase.execute(id2),
            getBookByIdUseCase.execute(id3)
        )
    }

    fun setAppIsRated(boolean: Boolean) {
        setAppIsRatedUseCase.execute(boolean)
    }

    fun setLastBook() {
        _lastBook.value = getLastBookReadUseCase.execute()
    }

    fun setReadingProgress(bookId: Int) {
        _lastBookReadingProgress.value = getReadingProgressUseCase.execute(bookId)
    }

    fun setLastSavedWord() {
        viewModelScope.launch {
            getAllWordsUseCase.invoke().collect {
                if (it.isNullOrEmpty()) _lastWord.value = null
                else _lastWord.value = it.last()
            }
        }
    }

    fun loadAd(context: Context, root: ViewGroup, onClickDismiss: View.OnClickListener) {
        AdMob.loadNativeAd(context = context, root = root, type = AdMob.AD_TYPE_01, onClickDismiss)
    }
}