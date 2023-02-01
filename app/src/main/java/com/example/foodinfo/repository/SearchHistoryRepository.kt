package com.example.foodinfo.repository

import com.example.foodinfo.repository.model.SearchInputModel


abstract class SearchHistoryRepository : BaseRepository() {
    abstract suspend fun getHistoryLatest(inputText: String): List<SearchInputModel>

    abstract fun getHistoryAll(): List<SearchInputModel>

    abstract fun addHistory(searchHistory: List<SearchInputModel>)

    abstract fun addInput(searchInput: SearchInputModel)
}