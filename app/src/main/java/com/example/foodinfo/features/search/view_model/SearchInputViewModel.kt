package com.example.foodinfo.features.search.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodinfo.domain.repository.SearchHistoryRepository
import com.example.foodinfo.features.search.interactor.SearchInputModelInteractor
import com.example.foodinfo.features.search.model.SearchInputVHModel
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
    private val searchHistoryInteractor: SearchInputModelInteractor,
    private val searchInputRepository: SearchHistoryRepository
) : ViewModel() {

    private val addCoroutine = CoroutineLauncher(
        viewModelScope, Dispatchers.IO, LaunchStrategy.IGNORE
    )

    private val updateCoroutine = CoroutineLauncher(
        viewModelScope, Dispatchers.IO, LaunchStrategy.CANCEL
    )

    private val _searchHistory = MutableSharedFlow<List<SearchInputVHModel>>(
        replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val searchHistory: SharedFlow<List<SearchInputVHModel>> = _searchHistory.shareIn(
        viewModelScope, SharingStarted.WhileSubscribed(), 0
    )


    init {
        viewModelScope.launch {
            withContext((Dispatchers.IO)) {
                _searchHistory.emit(searchHistoryInteractor.getHistory(""))
            }
        }
    }


    fun updateSearchHistory(inputText: String) {
        updateCoroutine.launch {
            _searchHistory.emit(searchHistoryInteractor.getHistory(inputText))
        }
    }

    fun addToHistory(inputText: String) {
        addCoroutine.launch {
            searchInputRepository.addInput(inputText)
        }
    }
}