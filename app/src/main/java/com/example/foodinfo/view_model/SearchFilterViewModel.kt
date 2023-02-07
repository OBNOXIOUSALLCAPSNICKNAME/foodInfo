package com.example.foodinfo.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodinfo.local.dto.SearchFilterDB
import com.example.foodinfo.repository.SearchFilterRepository
import com.example.foodinfo.repository.model.BasicOfSearchFilterEditModel
import com.example.foodinfo.repository.model.SearchFilterEditModel
import com.example.foodinfo.repository.use_case.SearchFilterUseCase
import com.example.foodinfo.utils.State
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject


class SearchFilterViewModel @Inject constructor(
    private val searchFilterRepository: SearchFilterRepository,
    private val searchFilterUseCase: SearchFilterUseCase,
) : ViewModel() {

    var filterName = SearchFilterDB.DEFAULT_NAME

    val filter: SharedFlow<State<SearchFilterEditModel>> by lazy {
        searchFilterUseCase.getFilterEdit().shareIn(
            viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000), 1
        )
    }


    fun reset() {
        searchFilterUseCase.resetFilter(filterName)
    }

    fun update(basics: List<BasicOfSearchFilterEditModel>) {
        searchFilterRepository.updateBasics(basics = basics)
    }
}
