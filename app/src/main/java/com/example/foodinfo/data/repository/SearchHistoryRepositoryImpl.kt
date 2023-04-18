package com.example.foodinfo.data.repository

import com.example.foodinfo.data.local.data_source.SearchHistoryLocalSource
import com.example.foodinfo.data.mapper.toDB
import com.example.foodinfo.data.mapper.toModel
import com.example.foodinfo.domain.model.SearchInput
import com.example.foodinfo.domain.repository.SearchHistoryRepository
import javax.inject.Inject


class SearchHistoryRepositoryImpl @Inject constructor(
    private val searchHistoryLocal: SearchHistoryLocalSource
) : BaseRepository(), SearchHistoryRepository {

    override suspend fun getHistoryLatest(inputText: String): List<SearchInput> {
        return searchHistoryLocal.getHistoryLatest(inputText).map { it.toModel() }
    }

    override suspend fun getHistoryAll(): List<SearchInput> {
        return searchHistoryLocal.getHistoryAll().map { it.toModel() }
    }

    override suspend fun addHistory(searchHistory: List<SearchInput>) {
        searchHistoryLocal.addHistory(searchHistory.map { it.toDB() })
    }

    override suspend fun addInput(searchInput: SearchInput) {
        searchHistoryLocal.addInput(searchInput.toDB())
    }
}