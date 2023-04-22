package com.example.foodinfo.features.search_filter.interactor

import com.example.foodinfo.domain.State
import com.example.foodinfo.domain.State.Utils.transformData
import com.example.foodinfo.domain.interactor.SearchFilterInteractor
import com.example.foodinfo.features.search_filter.mapper.toVHModel
import com.example.foodinfo.features.search_filter.model.SearchFilterModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class SearchFilterModelInteractor @Inject constructor(
    private val searchFilterInteractor: SearchFilterInteractor
) {

    fun getFilterEdit(): Flow<State<SearchFilterModel>> {
        return searchFilterInteractor.getFilter().transformData { searchFilter ->
            searchFilter.toVHModel()
        }
    }
}