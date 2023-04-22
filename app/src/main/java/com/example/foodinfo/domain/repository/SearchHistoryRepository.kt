package com.example.foodinfo.domain.repository

import com.example.foodinfo.domain.model.SearchInput


interface SearchHistoryRepository {
    suspend fun getHistory(inputText: String): List<SearchInput>

    suspend fun getHistoryAll(): List<SearchInput>

    suspend fun addHistory(searchHistory: List<SearchInput>)

    suspend fun addInput(inputText: String)
}