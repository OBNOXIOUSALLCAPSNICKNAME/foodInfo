package com.example.foodinfo.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodinfo.domain.model.SearchFilterEditModel
import com.example.foodinfo.domain.repository.SearchFilterRepository
import com.example.foodinfo.domain.state.State
import com.example.foodinfo.domain.use_case.SearchFilterUseCase
import com.example.foodinfo.utils.CoroutineLauncher
import com.example.foodinfo.utils.LaunchStrategy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject


class SearchFilterViewModel @Inject constructor(
    private val searchFilterRepository: SearchFilterRepository,
    private val searchFilterUseCase: SearchFilterUseCase,
) : ViewModel() {

    private val updateCoroutine = CoroutineLauncher(
        viewModelScope, Dispatchers.IO, LaunchStrategy.IGNORE
    )
    private val resetCoroutine = CoroutineLauncher(
        viewModelScope, Dispatchers.IO, LaunchStrategy.IGNORE
    )

    val filter: SharedFlow<State<SearchFilterEditModel>> by lazy {
        searchFilterUseCase.getFilterEdit().shareIn(
            viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000), 1
        )
    }


    fun reset() {
        resetCoroutine.launch {
            searchFilterRepository.resetFilter()
        }
    }

    fun update(id: Int, minValue: Float?, maxValue: Float?) {
        updateCoroutine.launch {
            searchFilterRepository.updateBasic(id, minValue, maxValue)
        }
    }
}