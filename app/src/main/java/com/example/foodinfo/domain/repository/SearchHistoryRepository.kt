package com.example.foodinfo.domain.repository

import com.example.foodinfo.domain.mapper.toDB
import com.example.foodinfo.domain.mapper.toModel
import com.example.foodinfo.domain.model.SearchInputModel
import com.example.foodinfo.domain.state.BaseRepository
import com.example.foodinfo.local.data_source.SearchHistoryLocalSource
import javax.inject.Inject


class SearchHistoryRepository @Inject constructor(
    private val searchHistoryLocal: SearchHistoryLocalSource
) : BaseRepository() {

    suspend fun getHistoryLatest(inputText: String): List<SearchInputModel> {
        return searchHistoryLocal.getHistoryLatest(inputText).map { it.toModel() }
    }

    suspend fun getHistoryAll(): List<SearchInputModel> {
        return searchHistoryLocal.getHistoryAll().map { it.toModel() }
    }

    suspend fun addHistory(searchHistory: List<SearchInputModel>) {
        searchHistoryLocal.addHistory(searchHistory.map { it.toDB() })
    }

    suspend fun addInput(searchInput: SearchInputModel) {
        searchHistoryLocal.addInput(searchInput.toDB())
    }
}