package com.example.foodinfo.local.room.data_source

import com.example.foodinfo.local.data_source.SearchHistoryLocalSource
import com.example.foodinfo.local.model.SearchInputDB
import com.example.foodinfo.local.room.dao.SearchHistoryDAO
import com.example.foodinfo.local.room.model.entity.SearchInputEntity
import javax.inject.Inject


class SearchHistoryRoomSource @Inject constructor(
    private val searchHistoryDAO: SearchHistoryDAO
) : SearchHistoryLocalSource {
    override suspend fun getHistoryLatest(inputText: String): List<SearchInputDB> {
        return searchHistoryDAO.getHistoryLatest(inputText)
    }

    override suspend fun getHistoryAll(): List<SearchInputDB> {
        return searchHistoryDAO.getHistoryAll()
    }

    override suspend fun addHistory(searchInput: List<SearchInputDB>) {
        searchHistoryDAO.addHistory(searchInput.map(SearchInputEntity::invoke))
    }

    override suspend fun addInput(searchInput: SearchInputDB) {
        searchHistoryDAO.addInput(SearchInputEntity(searchInput))
    }
}