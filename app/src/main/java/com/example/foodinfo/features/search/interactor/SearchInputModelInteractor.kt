package com.example.foodinfo.features.search.interactor

import com.example.foodinfo.domain.model.SearchInput
import com.example.foodinfo.domain.repository.SearchHistoryRepository
import com.example.foodinfo.features.search.mapper.toVHModel
import com.example.foodinfo.features.search.model.SearchInputVHModel
import javax.inject.Inject


class SearchInputModelInteractor @Inject constructor(
    private val searchInputRepository: SearchHistoryRepository
) {

    suspend fun getHistory(inputText: String): List<SearchInputVHModel> {
        return searchInputRepository.getHistory(inputText).map(SearchInput::toVHModel)
    }
}