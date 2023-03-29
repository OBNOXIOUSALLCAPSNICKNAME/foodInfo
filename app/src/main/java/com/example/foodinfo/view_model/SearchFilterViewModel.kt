package com.example.foodinfo.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodinfo.repository.SearchFilterRepository
import com.example.foodinfo.repository.model.SearchFilterEditModel
import com.example.foodinfo.repository.use_case.SearchFilterUseCase
import com.example.foodinfo.repository.state_handling.State
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject


class SearchFilterViewModel @Inject constructor(
    private val searchFilterRepository: SearchFilterRepository,
    private val searchFilterUseCase: SearchFilterUseCase,
) : ViewModel() {

    val filter: SharedFlow<State<SearchFilterEditModel>> by lazy {
        searchFilterUseCase.getFilterEdit().shareIn(
            viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000), 1
        )
    }


    fun reset() {
        searchFilterRepository.resetFilter()
    }

    fun update(id: Int, minValue: Float?, maxValue: Float?) {
        searchFilterRepository.updateBasic(id, minValue, maxValue)
    }
}
