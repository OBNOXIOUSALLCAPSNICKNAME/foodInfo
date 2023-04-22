package com.example.foodinfo.data.local.room.data_source

import com.example.foodinfo.data.local.data_source.SearchHistoryLocalSource
import com.example.foodinfo.data.local.model.SearchInputDB
import com.example.foodinfo.data.local.room.dao.SearchHistoryDAO
import com.example.foodinfo.data.local.room.model.entity.SearchInputEntity
import javax.inject.Inject


class SearchHistoryRoomSource @Inject constructor(
    private val searchHistoryDAO: SearchHistoryDAO
) : SearchHistoryLocalSource {
    override suspend fun getHistory(inputText: String): List<SearchInputDB> {
        return searchHistoryDAO.getHistory(inputText)
    }

    override suspend fun getHistoryAll(): List<SearchInputDB> {
        return searchHistoryDAO.getHistoryAll()
    }

    override suspend fun addHistory(searchInput: List<SearchInputDB>) {
        searchHistoryDAO.addHistory(searchInput.map(SearchInputEntity::invoke))
    }

    override suspend fun addInput(inputText: String) {
        searchHistoryDAO.addInput(SearchInputEntity(inputText = inputText))
    }
}