package com.example.foodinfo.repository.impl

import com.example.foodinfo.local.dao.SearchHistoryDAO
import com.example.foodinfo.repository.SearchHistoryRepository
import com.example.foodinfo.repository.mapper.toDB
import com.example.foodinfo.repository.mapper.toModel
import com.example.foodinfo.repository.model.SearchInputModel
import javax.inject.Inject


class SearchHistoryRepositoryImpl @Inject constructor(
    private val searchHistoryDAO: SearchHistoryDAO
) : SearchHistoryRepository() {

    override suspend fun getHistoryLatest(inputText: String): List<SearchInputModel> {
        return searchHistoryDAO.getHistoryLatest(inputText).map { it.toModel() }
    }

    override fun getHistoryAll(): List<SearchInputModel> {
        return searchHistoryDAO.getHistoryAll().map { it.toModel() }
    }

    override fun addHistory(searchHistory: List<SearchInputModel>) {
        searchHistoryDAO.addHistory(searchHistory.map { it.toDB() })
    }

    override fun addInput(searchInput: SearchInputModel) {
        searchHistoryDAO.addInput(searchInput.toDB())
    }
}