package com.example.foodinfo.repository

import com.example.foodinfo.local.dao.SearchHistoryDAO
import com.example.foodinfo.repository.mapper.toDB
import com.example.foodinfo.repository.mapper.toModel
import com.example.foodinfo.repository.model.SearchInputModel
import com.example.foodinfo.repository.state_handling.BaseRepository
import javax.inject.Inject


class SearchHistoryRepository @Inject constructor(
    private val searchHistoryDAO: SearchHistoryDAO
) : BaseRepository() {

    suspend fun getHistoryLatest(inputText: String): List<SearchInputModel> {
        return searchHistoryDAO.getHistoryLatest(inputText).map { it.toModel() }
    }

    fun getHistoryAll(): List<SearchInputModel> {
        return searchHistoryDAO.getHistoryAll().map { it.toModel() }
    }

    fun addHistory(searchHistory: List<SearchInputModel>) {
        searchHistoryDAO.addHistory(searchHistory.map { it.toDB() })
    }

    fun addInput(searchInput: SearchInputModel) {
        searchHistoryDAO.addInput(searchInput.toDB())
    }
}