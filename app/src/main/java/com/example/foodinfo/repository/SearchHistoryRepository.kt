package com.example.foodinfo.repository

import com.example.foodinfo.repository.model.SearchInputModel


interface SearchHistoryRepository {
    suspend fun getHistoryLatest(inputText: String): List<SearchInputModel>

    fun getHistoryAll(): List<SearchInputModel>

    fun addHistory(searchHistory: List<SearchInputModel>)

    fun addInput(searchInput: SearchInputModel)
}