package com.example.foodinfo.data.repository

import com.example.foodinfo.data.local.data_source.SearchHistoryLocalSource
import com.example.foodinfo.data.local.model.SearchInputDB
import com.example.foodinfo.data.mapper.toDB
import com.example.foodinfo.data.mapper.toModel
import com.example.foodinfo.domain.model.SearchInput
import com.example.foodinfo.domain.repository.SearchHistoryRepository
import javax.inject.Inject


class SearchHistoryRepositoryImpl @Inject constructor(
    private val searchHistoryLocal: SearchHistoryLocalSource
) : BaseRepository(), SearchHistoryRepository {

    override suspend fun getHistory(inputText: String): List<SearchInput> {
        return searchHistoryLocal.getHistory(inputText).map(SearchInputDB::toModel)
    }

    override suspend fun getHistoryAll(): List<SearchInput> {
        return searchHistoryLocal.getHistoryAll().map(SearchInputDB::toModel)
    }

    override suspend fun addHistory(searchHistory: List<SearchInput>) {
        searchHistoryLocal.addHistory(searchHistory.map(SearchInput::toDB))
    }

    override suspend fun addInput(inputText: String) {
        searchHistoryLocal.addInput(inputText)
    }
}