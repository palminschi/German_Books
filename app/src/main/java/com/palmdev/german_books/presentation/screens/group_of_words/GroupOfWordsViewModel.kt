package com.palmdev.german_books.presentation.screens.group_of_words

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.palmdev.domain.model.Word
import com.palmdev.domain.usecase.purchases.GetPremiumStatusUseCase
import com.palmdev.domain.usecase.words.DeleteByGroupUseCase
import com.palmdev.domain.usecase.words.GetWordsByGroupUseCase
import kotlinx.coroutines.launch

class GroupOfWordsViewModel(
    private val getWordsByGroupUseCase: GetWordsByGroupUseCase,
    private val getPremiumStatusUseCase: GetPremiumStatusUseCase,
    private val deleteByGroupUseCase: DeleteByGroupUseCase
) : ViewModel() {

    private val _words = MutableLiveData<List<Word>>()
    val words: LiveData<List<Word>> = _words

    private val _userPremiumStatus = MutableLiveData<Boolean>()
    val userPremiumStatus: LiveData<Boolean> = _userPremiumStatus

    init {
        _userPremiumStatus.value = getPremiumStatusUseCase.execute()
    }

    fun getWords(groupId: Int) {
        if (_words.value.isNullOrEmpty()) {
            viewModelScope.launch {
                getWordsByGroupUseCase.invoke(groupId = groupId).collect {
                    _words.value = it
                }
            }
        }
    }

    fun deleteWords(groupId: Int) {
        viewModelScope.launch {
            deleteByGroupUseCase.execute(groupId = groupId)
        }
    }
}