package com.example.foodinfo.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodinfo.local.dto.SearchFilterDB
import com.example.foodinfo.repository.SearchFilterRepository
import com.example.foodinfo.repository.model.SearchFilterEditModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject


class SearchFilterViewModel @Inject constructor(
    private val searchFilterRepository: SearchFilterRepository,
) : ViewModel() {

    val filter: SharedFlow<SearchFilterEditModel> by lazy {
        searchFilterRepository.getFilterEdit().shareIn(
            viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000), 1
        )
    }

    var filterName = SearchFilterDB.DEFAULT_NAME


    fun reset() {
        searchFilterRepository.resetFilter()
    }

    fun updateField(id: Int, minValue: Float, maxValue: Float) {
        searchFilterRepository.updateBaseField(id, minValue, maxValue)
    }
}
