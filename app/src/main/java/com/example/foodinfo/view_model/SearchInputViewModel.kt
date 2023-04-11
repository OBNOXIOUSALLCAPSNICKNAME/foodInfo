package com.example.foodinfo.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodinfo.domain.repository.SearchHistoryRepository
import com.example.foodinfo.domain.model.SearchInputModel
import com.example.foodinfo.utils.CoroutineLauncher
import com.example.foodinfo.utils.LaunchStrategy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


class SearchInputViewModel @Inject constructor(
    private val searchHistoryRepository: SearchHistoryRepository
) : ViewModel() {

    private val addCoroutine = CoroutineLauncher(
        viewModelScope, Dispatchers.IO, LaunchStrategy.IGNORE
    )

    private val updateCoroutine = CoroutineLauncher(
        viewModelScope, Dispatchers.IO, LaunchStrategy.CANCEL
    )

    private val _searchHistory = MutableSharedFlow<List<SearchInputModel>>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val searchHistory: SharedFlow<List<SearchInputModel>> = _searchHistory.shareIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        0
    )


    init {
        viewModelScope.launch {
            withContext((Dispatchers.IO)) {
                _searchHistory.emit(searchHistoryRepository.getHistoryLatest(""))
            }
        }
    }


    fun updateSearchHistory(inputText: String = "") {
        updateCoroutine.launch {
            _searchHistory.emit(searchHistoryRepository.getHistoryLatest(inputText))
        }
    }

    fun addToHistory(inputText: String) {
        addCoroutine.launch {
            searchHistoryRepository.addInput(SearchInputModel(inputText = inputText))
        }
    }
}