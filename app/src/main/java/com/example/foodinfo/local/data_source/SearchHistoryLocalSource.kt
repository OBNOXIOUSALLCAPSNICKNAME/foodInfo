package com.example.foodinfo.local.data_source

import com.example.foodinfo.local.model.SearchInputDB


interface SearchHistoryLocalSource {

    // select top 7 rows filtered by "date" column
    suspend fun getHistoryLatest(inputText: String): List<SearchInputDB>

    suspend fun getHistoryAll(): List<SearchInputDB>

    // add functions must update content if it already in DB
    suspend fun addHistory(searchInput: List<SearchInputDB>)

    suspend fun addInput(searchInput: SearchInputDB)
}